package com.theblackbit.rickandmorty.feature.character.domain

import com.theblackbit.rickandmorty.core.model.Character

interface CharacterInfoUseCase {
    suspend fun getCharacterInfo(id: Int): Character?
}
