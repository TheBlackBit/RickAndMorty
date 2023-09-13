package com.theblackbit.rickandmorty.core.localstorage.di

import com.theblackbit.rickandmorty.core.localstorage.room.RickAndMortyRoom
import com.theblackbit.rickandmorty.core.localstorage.room.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDaoModule {

    @Provides
    @Singleton
    fun providesCharacterDao(rickAndMortyRoom: RickAndMortyRoom): CharacterDao {
        return rickAndMortyRoom.characterDao()
    }
}
