package com.hooman.einkaufszettel.di

import com.hooman.einkaufszettel.presentation.util.internet_connection.ConnectivityObserver
import com.hooman.einkaufszettel.presentation.util.internet_connection.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    @Singleton
    abstract fun bindNetworkConnectivityObserver(
        networkConnectivityObserver: NetworkConnectivityObserver
    ):ConnectivityObserver
}