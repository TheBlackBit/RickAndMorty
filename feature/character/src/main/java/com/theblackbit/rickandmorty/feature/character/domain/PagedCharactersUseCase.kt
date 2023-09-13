package com.theblackbit.rickandmorty.feature.character.domain

import androidx.paging.Pager
import com.theblackbit.rickandmorty.core.model.Character

interface PagedCharactersUseCase {
    fun collectPagedCharacters(): Pager<Int, Character>
}
