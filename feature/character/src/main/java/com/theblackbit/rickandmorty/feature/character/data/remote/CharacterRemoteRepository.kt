package com.theblackbit.rickandmorty.feature.character.data.remote

import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus

interface CharacterRemoteRepository {
    suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>>
    suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?>
}
