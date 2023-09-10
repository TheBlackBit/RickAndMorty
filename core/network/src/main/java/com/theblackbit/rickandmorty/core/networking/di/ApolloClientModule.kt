package com.theblackbit.rickandmorty.core.networking.di

import com.apollographql.apollo3.ApolloClient
import com.theblackbit.rickandmorty.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloClientModule {
    @Provides
    @Singleton
    fun providesApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
    }
}
