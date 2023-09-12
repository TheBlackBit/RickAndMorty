package com.theblackbit.rickandmorty.core.model

data class Character(
    val id: Int,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val image: String = "",
    val height: Int = 200
)
