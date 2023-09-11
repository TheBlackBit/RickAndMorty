package com.theblackbit.rickandmorty.core.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theblackbit.rickandmorty.core.localstorage.dao.CharacterDao
import com.theblackbit.rickandmorty.core.localstorage.entity.CharacterEntity

const val LATEST_VERSION = 1

@Database(
    version = LATEST_VERSION,
    entities = [
        CharacterEntity::class
    ],
    exportSchema = true
)
abstract class RickAndMortyRoom : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
