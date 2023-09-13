package com.theblackbit.rickandmorty.feature.character.di

import com.theblackbit.rickandmorty.core.networking.datasource.RemoteDataSource
import com.theblackbit.rickandmorty.feature.character.data.remote.CharacterRemoteRepository
import com.theblackbit.rickandmorty.feature.character.data.remote.CharacterRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterRemoteRepositoryModule {

    @Provides
    @Singleton
    fun providesCharactersRemoteRepository(remoteDataSource: RemoteDataSource): CharacterRemoteRepository {
        return CharacterRemoteRepositoryImpl(remoteDataSource)
    }
}
