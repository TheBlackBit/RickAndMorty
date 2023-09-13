package com.theblackbit.rickandmorty.feature.character.data.remote

import com.theblackbit.rickandmorty.core.networking.datasource.RemoteDataSource
import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus

class CharacterRemoteRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : CharacterRemoteRepository {
    override suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>> {
        return remoteDataSource.getCharacters(page)
    }

    override suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?> {
        return remoteDataSource.getCharacter(id)
    }
}
