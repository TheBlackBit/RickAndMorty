package com.theblackbit.rickandmorty.feature.character.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.insertHeaderItem
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.core.util.IoDispatcher
import com.theblackbit.rickandmorty.feature.character.domain.CharacterInfoUseCase
import com.theblackbit.rickandmorty.feature.character.domain.PagedCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val pagedCharactersUseCase: PagedCharactersUseCase,
    private val characterInfoUseCase: CharacterInfoUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @IoDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterInfoStateFlow: MutableStateFlow<Character?> = MutableStateFlow(null)
    val characterInfoStateFlow: StateFlow<Character?> = _characterInfoStateFlow.asStateFlow()
    fun collectCharacters(): Flow<PagingData<Character>> {
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
    }

    fun getCharacter(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            val character = characterInfoUseCase.getCharacterInfo(id)
            withContext(mainDispatcher) {
                _characterInfoStateFlow.value = character
            }
        }
    }
}
