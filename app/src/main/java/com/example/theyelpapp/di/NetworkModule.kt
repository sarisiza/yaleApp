package com.example.theyelpapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.theyelpapp.data.service.CacheInterceptor
import com.example.theyelpapp.data.service.ForceCacheInterceptor
import com.example.theyelpapp.data.service.RequestInterceptor
import com.example.theyelpapp.data.service.YelpApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesYelpApi(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): YelpApi =
        Retrofit.Builder()
            .baseUrl(YelpApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(YelpApi::class.java)

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesOkHttpClient(
        cache: Cache,
        requestInterceptor: RequestInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cacheInterceptor: CacheInterceptor,
        forceCacheInterceptor: ForceCacheInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(cacheInterceptor)
            .addInterceptor(forceCacheInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideCacheFile(
        @ApplicationContext context: Context
    ): Cache =
        Cache(File(context.cacheDir, "http-cache"), 10L*1024L*1024L)

}