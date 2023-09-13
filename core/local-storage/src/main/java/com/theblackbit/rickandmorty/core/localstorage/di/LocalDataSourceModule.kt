package com.theblackbit.rickandmorty.core.localstorage.di

import com.theblackbit.rickandmorty.core.localstorage.datasource.LocalDataSource
import com.theblackbit.rickandmorty.core.localstorage.datasource.RoomDataSource
import com.theblackbit.rickandmorty.core.localstorage.room.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun providesLocalStorageModule(
        characterDao: CharacterDao
    ): LocalDataSource {
        return RoomDataSource(characterDao)
    }
}
