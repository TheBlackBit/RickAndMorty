package com.theblackbit.rickandmorty.feature.character.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.feature.character.domain.PagedCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val pagedCharactersUseCase: PagedCharactersUseCase
) : ViewModel() {

    fun collectCharacters(
        scope: CoroutineScope
    ): Flow<PagingData<Character>> {
        return pagedCharactersUseCase
            .collectPagedCharacters()
            .flow
            .map {
                val headers = listOf(
                    Character(-1),
                    Character(-2)
                )
                headers.fold(it) { data, header ->
                    data.insertHeaderItem(TerminalSeparatorType.FULLY_COMPLETE, header)
                }
            }
            .cachedIn(scope)
    }
}
