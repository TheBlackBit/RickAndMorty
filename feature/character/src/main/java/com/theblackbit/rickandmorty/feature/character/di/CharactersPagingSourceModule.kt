package com.theblackbit.rickandmorty.feature.character.di

import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository
import com.theblackbit.rickandmorty.feature.character.data.pagingsource.CharactersPagingSource
import com.theblackbit.rickandmorty.feature.character.data.remote.CharacterRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersPagingSourceModule {

    @Provides
    @Singleton
    fun providesCharactersPagingSource(
        characterLocalRepository: CharacterLocalRepository,
        characterRemoteRepository: CharacterRemoteRepository
    ): CharactersPagingSource {
        return CharactersPagingSource(characterLocalRepository, characterRemoteRepository)
    }
}
