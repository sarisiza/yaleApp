package com.example.theyelpapp.di

import com.example.theyelpapp.datalayer.database.LocalRepository
import com.example.theyelpapp.datalayer.database.LocalRepositoryImpl
import com.example.theyelpapp.datalayer.location.LocationRepository
import com.example.theyelpapp.datalayer.location.LocationRepositoryImpl
import com.example.theyelpapp.datalayer.service.NetworkRepository
import com.example.theyelpapp.datalayer.service.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideNetworkRepository(
        networkRepositoryImpl: NetworkRepositoryImpl
    ): NetworkRepository

    @Binds
    abstract fun provideLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun provideLocalRepository(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository

}