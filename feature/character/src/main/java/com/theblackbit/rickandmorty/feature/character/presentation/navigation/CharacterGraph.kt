package com.theblackbit.rickandmorty.feature.character.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.theblackbit.rickandmorty.feature.character.presentation.CharactersViewModel
import com.theblackbit.rickandmorty.feature.character.presentation.screens.detail.CharacterDetailScreen
import com.theblackbit.rickandmorty.feature.character.presentation.screens.listcharacters.CharactersScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharacterGraph() {
    val navController = rememberAnimatedNavController()

    val viewModel: CharactersViewModel = hiltViewModel()

    val characters = viewModel.collectCharacters(viewModel.viewModelScope).collectAsLazyPagingItems()

    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.CharactersRoute.route
    ) {
        navigationItems.forEach { routes ->
            when (routes) {
                Routes.CharactersRoute -> {
                    slideRoute(route = routes.route) {
                        val scrollState = rememberLazyStaggeredGridState()

                        CharactersScreen(
                            pagingItems = characters,
                            scrollState = scrollState,
                            onClickItem = { id, image ->
                                navController.navigate("$characterRootRouteName/$id/$image") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }

                Routes.CharacterRoute -> {
                    slideRoute(
                        route = routes.route,
                        arguments = routes.arguments
                    ) { navBackStackEntry ->

                        val id = navBackStackEntry.arguments?.getInt(routes.arguments[0].name)
                        val image = navBackStackEntry.arguments?.getString(routes.arguments[1].name)

                        val character = viewModel.characterInfoStateFlow.collectAsStateWithLifecycle()

                        LaunchedEffect(Unit) {
                            id?.let { viewModel.getCharacter(it) }
                        }

                        CharacterDetailScreen(character = character.value, imageUrl = image ?: "") {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}
