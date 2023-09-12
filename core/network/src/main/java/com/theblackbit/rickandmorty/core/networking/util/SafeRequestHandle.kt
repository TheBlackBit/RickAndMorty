package com.theblackbit.rickandmorty.core.networking.util

import com.apollographql.apollo3.api.Error

sealed class SafeRequestHandle<out T> {
    data class Success<out T>(val value: T) : SafeRequestHandle<T>()
    data class ApiError(val listErrors: List<Error>) : SafeRequestHandle<Nothing>()
    data object NetworkError : SafeRequestHandle<Nothing>()
}
