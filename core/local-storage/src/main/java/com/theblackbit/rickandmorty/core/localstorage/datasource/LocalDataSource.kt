package com.theblackbit.rickandmorty.core.localstorage.datasource

import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity

interface LocalDataSource {
    suspend fun getCharacters(pageNumber: Int): List<CharacterEntity>
    suspend fun getCharacter(id: Int): CharacterEntity?
    suspend fun upsertCharacter(characters: List<CharacterEntity>)
    suspend fun deleteCharacter(pageNumber: Int)
}
