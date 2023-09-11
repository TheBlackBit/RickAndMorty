package com.theblackbit.rickandmorty.core.localstorage.di

import android.content.Context
import androidx.room.Room
import com.theblackbit.rickandmorty.core.localstorage.RickAndMortyRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    private const val DB_NAME = "rick_and_morty_db"

    @Provides
    @Singleton
    fun providesLocalStorageModule(@ApplicationContext context: Context): RickAndMortyRoom {
        return Room
            .databaseBuilder(context, RickAndMortyRoom::class.java, DB_NAME)
            .build()
    }
}
