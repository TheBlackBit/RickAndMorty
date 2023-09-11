package com.theblackbit.rickandmorty.feature.character.data.local

import com.theblackbit.rickandmorty.core.localstorage.entity.CharacterEntity

interface CharacterLocalRepository {
    suspend fun getCharacters(pageNumber: Int): List<CharacterEntity>
    suspend fun getCharacter(id: Int): CharacterEntity?
    suspend fun upsertCharacter(characters: List<CharacterEntity>)
    suspend fun deleteCharacter(pageNumber: Int)
}
