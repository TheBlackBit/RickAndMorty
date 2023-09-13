package com.theblackbit.rickandmorty.core.model

import com.theblackbit.rickandmorty.core.resources.R
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

fun Character?.statusIcon(): Int {
    return if (this?.status?.equals("Dead") == true) R.raw.alive else R.raw.dead
}

fun Character?.genderIcon(): Int {
    return if (this?.gender?.equals("Male") == true) R.drawable.baseline_male_24 else R.drawable.baseline_female_24
}
