package com.theblackbit.rickandmorty.core.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Character(
    val id: Int,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val image: String = "",
    val height: Int = 200
)

fun Character.encodedImageUrl(): String {
    return URLEncoder.encode(image, StandardCharsets.UTF_8.toString())
}

fun Character.isAlive(): Boolean {
    return status != "Dead"
}

fun Character.isMale(): Boolean {
    return gender == "Male"
}
