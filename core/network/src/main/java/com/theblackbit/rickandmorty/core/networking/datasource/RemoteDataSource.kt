package com.theblackbit.rickandmorty.core.networking.datasource

import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus

interface RemoteDataSource {
    suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>>
    suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?>
}
