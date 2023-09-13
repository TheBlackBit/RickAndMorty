package com.theblackbit.rickandmorty.core.networking.di

import com.apollographql.apollo3.ApolloClient
import com.theblackbit.rickandmorty.core.networking.datasource.ApolloClientDataSource
import com.theblackbit.rickandmorty.core.networking.datasource.RemoteDataSource
import com.theblackbit.rickandmorty.core.networking.util.SafeRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(apolloClient: ApolloClient): RemoteDataSource {
        return ApolloClientDataSource(apolloClient, SafeRequest())
    }
}
