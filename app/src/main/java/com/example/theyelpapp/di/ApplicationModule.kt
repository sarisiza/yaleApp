package com.example.theyelpapp.di

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.example.theyelpapp.LocationService
import com.example.theyelpapp.datalayer.location.LocationRepository
import com.example.theyelpapp.datalayer.service.NetworkRepository
import com.example.theyelpapp.usecaseslayer.GetLocationUseCase
import com.example.theyelpapp.usecaseslayer.GetRatingsUseCase
import com.example.theyelpapp.usecaseslayer.GetRestaurantsUseCase
import com.example.theyelpapp.usecaseslayer.YelpUseCases
import com.example.theyelpapp.utils.NetworkState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("ServiceCast")
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun providesConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun providesFusedLocation(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesYelpUseCases(
        locationRepository: LocationRepository,
        networkRepository: NetworkRepository,
        networkState: NetworkState
    ): YelpUseCases =
        YelpUseCases(
            GetLocationUseCase(locationRepository),
            GetRatingsUseCase(networkRepository,networkState),
            GetRestaurantsUseCase(networkRepository,networkState)
        )

    @Provides
    @Singleton
    fun providesLocationManager(
        @ApplicationContext context: Context
    ): LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

}