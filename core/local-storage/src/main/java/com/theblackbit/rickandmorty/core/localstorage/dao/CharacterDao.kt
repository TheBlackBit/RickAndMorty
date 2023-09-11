package com.theblackbit.rickandmorty.core.localstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.theblackbit.rickandmorty.core.localstorage.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Upsert
    suspend fun upsertCharacters(characterEntities: List<CharacterEntity>)

    @Query("SELECT * from characterentity where page =:pageNumber")
    suspend fun getCharacters(pageNumber: Int): List<CharacterEntity>

    @Query("SELECT * from characterentity where id =:id")
    suspend fun getCharacter(id: Int): CharacterEntity?

    @Query("DELETE from characterentity where page= :pageNumber")
    suspend fun deleteCharacter(pageNumber: Int)
}
