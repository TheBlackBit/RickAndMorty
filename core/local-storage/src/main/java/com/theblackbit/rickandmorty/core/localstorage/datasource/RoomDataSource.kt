package com.theblackbit.rickandmorty.core.localstorage.datasource

import com.theblackbit.rickandmorty.core.localstorage.room.dao.CharacterDao
import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity

class RoomDataSource(
    private val characterDao: CharacterDao
) : LocalDataSource {
    override suspend fun getCharacters(pageNumber: Int): List<CharacterEntity> {
        return characterDao.getCharacters(pageNumber)
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? {
        return characterDao.getCharacter(id)
    }

    override suspend fun upsertCharacter(characters: List<CharacterEntity>) {
        characterDao.upsertCharacters(characters)
    }

    override suspend fun deleteCharacter() {
        characterDao.deleteCharacter()
    }
}
