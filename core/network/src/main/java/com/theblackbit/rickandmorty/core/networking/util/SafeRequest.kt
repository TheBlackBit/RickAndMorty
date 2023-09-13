package com.theblackbit.rickandmorty.core.networking.util

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.ApolloException

class SafeRequest {
    suspend fun <D : Operation.Data> request(
        call: ApolloCall<D>
    ): SafeRequestHandle<ApolloResponse<D>> {
        return try {
            val result = call.execute()
            if (result.hasErrors()) {
                SafeRequestHandle.ApiError(result.errors ?: emptyList())
            } else {
                SafeRequestHandle.Success(result)
            }
        } catch (e: ApolloException) {
            SafeRequestHandle.NetworkError
        }
    }
}
