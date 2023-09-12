package com.theblackbit.rickandmorty.feature.character.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val charactersRootRouteName = "characters"
const val characterRootRouteName = "character"
const val characterIdArgument = "id"
const val characterImageArgument = "image"
sealed class Routes(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    data object CharactersRoute : Routes(charactersRootRouteName)
    data object CharacterRoute : Routes(
        "$characterRootRouteName/{$characterIdArgument}/{$characterImageArgument}",
        arguments = listOf(
            navArgument(characterIdArgument) { type = NavType.IntType },
            navArgument(characterImageArgument) { type = NavType.StringType }
        )
    )
}

val navigationItems = listOf(
    Routes.CharactersRoute,
    Routes.CharacterRoute
)
