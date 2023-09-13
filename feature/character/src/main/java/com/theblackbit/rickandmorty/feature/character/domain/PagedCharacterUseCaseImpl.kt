package com.theblackbit.rickandmorty.feature.character.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.theblackbit.rickandmorty.core.model.Character

class PagedCharacterUseCaseImpl(
    private val pagingSource: PagingSource<Int, Character>,
    private val pagingConfig: PagingConfig
) : PagedCharactersUseCase {
    override fun collectPagedCharacters(): Pager<Int, Character> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                pagingSource
            }
        )
    }
}
