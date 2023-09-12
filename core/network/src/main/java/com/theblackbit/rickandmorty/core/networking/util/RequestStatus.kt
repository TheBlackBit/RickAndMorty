package com.theblackbit.rickandmorty.core.networking.util

import com.apollographql.apollo3.api.Error

sealed class RequestStatus<out T> {
    data class Success<out T>(val value: T) : RequestStatus<T>()
    data class ApiError(val listErrors: List<Error>) : RequestStatus<Nothing>()
    data object NetworkError : RequestStatus<Nothing>()
}
