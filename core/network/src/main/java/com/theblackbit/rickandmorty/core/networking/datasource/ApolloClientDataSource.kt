package com.theblackbit.rickandmorty.core.networking.datasource

import com.apollographql.apollo3.ApolloClient
import com.theblackbit.rickandmorty.CharacterQuery
import com.theblackbit.rickandmorty.CharactersQuery
import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus
import com.theblackbit.rickandmorty.core.networking.util.SafeRequest
import com.theblackbit.rickandmorty.core.networking.util.SafeRequestHandle

class ApolloClientDataSource(
    private val apolloClient: ApolloClient,
    private val safeRequest: SafeRequest
) : RemoteDataSource {
    override suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>> {
        val call = apolloClient.query(CharactersQuery(page))
        return when (val result = safeRequest.request(call)) {
            is SafeRequestHandle.Success -> {
                val data = result
                    .value.data
                    ?.characters
                    ?.results
                    ?.map {
                        CharacterResponse(
                            id = it?.id?.toIntOrNull() ?: -1,
                            name = it?.name,
                            status = it?.status,
                            species = it?.species,
                            gender = it?.gender,
                            image = it?.image
                        )
                    } ?: emptyList()
                RequestStatus.Success(data)
            }

            is SafeRequestHandle.ApiError -> RequestStatus.ApiError(result.listErrors)
            is SafeRequestHandle.NetworkError -> RequestStatus.NetworkError
        }
    }

    override suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?> {
        val call = apolloClient.query(CharacterQuery(id.toString()))
        return when (val result = safeRequest.request(call)) {
            is SafeRequestHandle.Success -> {
                val data = result
                    .value.data
                    ?.character
                    ?.run {
                        CharacterResponse(
                            id = this.id?.toIntOrNull() ?: -1,
                            name = this.name,
                            status = this.status,
                            species = this.species,
                            gender = this.gender,
                            image = this.image
                        )
                    }
                RequestStatus.Success(data)
            }

            is SafeRequestHandle.ApiError -> RequestStatus.ApiError(result.listErrors)
            is SafeRequestHandle.NetworkError -> RequestStatus.NetworkError
        }
    }
}
