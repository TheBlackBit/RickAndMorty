package com.theblackbit.rickandmorty.feature.character.data.local

import com.theblackbit.rickandmorty.core.localstorage.dao.CharacterDao
import com.theblackbit.rickandmorty.core.localstorage.entity.CharacterEntity

class CharacterLocalRepositoryImpl(
    private val characterDao: CharacterDao
) : CharacterLocalRepository {
    override suspend fun getCharacters(pageNumber: Int): List<CharacterEntity> {
        return characterDao.getCharacters(pageNumber)
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? {
        return characterDao.getCharacter(id)
    }

    override suspend fun upsertCharacter(characters: List<CharacterEntity>) {
        characterDao.upsertCharacters(characters)
    }

    override suspend fun deleteCharacter(pageNumber: Int) {
        characterDao.deleteCharacter(pageNumber)
    }
}
