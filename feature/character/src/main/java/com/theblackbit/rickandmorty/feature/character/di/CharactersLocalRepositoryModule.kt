package com.theblackbit.rickandmorty.feature.character.di

import com.theblackbit.rickandmorty.core.localstorage.datasource.LocalDataSource
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersLocalRepositoryModule {

    @Provides
    @Singleton
    fun providesCharactersLocalRepository(localDataSource: LocalDataSource): CharacterLocalRepository {
        return CharacterLocalRepositoryImpl(localDataSource)
    }
}
