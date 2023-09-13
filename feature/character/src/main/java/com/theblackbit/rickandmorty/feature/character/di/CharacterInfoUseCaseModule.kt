package com.theblackbit.rickandmorty.feature.character.di

import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository
import com.theblackbit.rickandmorty.feature.character.domain.CharacterInfoUseCase
import com.theblackbit.rickandmorty.feature.character.domain.CharacterInfoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CharacterInfoUseCaseModule {
    @Provides
    @ViewModelScoped
    fun providesCharacterInfoUseCase(localRepository: CharacterLocalRepository): CharacterInfoUseCase {
        return CharacterInfoUseCaseImpl(localRepository)
    }
}
