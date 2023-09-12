package com.theblackbit.rickandmorty.feature.character.di

import androidx.paging.PagingConfig
import com.theblackbit.rickandmorty.feature.character.data.pagingsource.CharactersPagingSource
import com.theblackbit.rickandmorty.feature.character.domain.PagedCharacterUseCaseImpl
import com.theblackbit.rickandmorty.feature.character.domain.PagedCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object PagedCharactersUseCaseModule {

    private const val pageSize = 20

    @Provides
    @ViewModelScoped
    fun providesPagedCharactersUseCase(
        charactersPagingSource: CharactersPagingSource
    ): PagedCharactersUseCase {
        return PagedCharacterUseCaseImpl(
            charactersPagingSource = charactersPagingSource,
            pagingConfig = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = pageSize / 2,
            )
        )
    }
}