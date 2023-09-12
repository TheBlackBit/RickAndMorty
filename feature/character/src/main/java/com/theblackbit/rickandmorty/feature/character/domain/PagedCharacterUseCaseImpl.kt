package com.theblackbit.rickandmorty.feature.character.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.feature.character.data.pagingsource.CharactersPagingSource

class PagedCharacterUseCaseImpl(
    private val charactersPagingSource: CharactersPagingSource,
    private val pagingConfig: PagingConfig
) : PagedCharactersUseCase {
    override fun collectPagedCharacters(): Pager<Int, Character> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                charactersPagingSource
            }
        )
    }
}
