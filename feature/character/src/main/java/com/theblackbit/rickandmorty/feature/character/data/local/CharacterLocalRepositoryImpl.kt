package com.theblackbit.rickandmorty.feature.character.data.local

import com.theblackbit.rickandmorty.core.localstorage.datasource.LocalDataSource
import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity

class CharacterLocalRepositoryImpl(
    private val localDataSource: LocalDataSource
) : CharacterLocalRepository {
    override suspend fun getCharacters(pageNumber: Int): List<CharacterEntity> {
        return localDataSource.getCharacters(pageNumber)
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? {
        return localDataSource.getCharacter(id)
    }

    override suspend fun upsertCharacter(characters: List<CharacterEntity>) {
        localDataSource.upsertCharacter(characters)
    }

    override suspend fun deleteCharacter() {
        localDataSource.deleteCharacter()
    }
}
