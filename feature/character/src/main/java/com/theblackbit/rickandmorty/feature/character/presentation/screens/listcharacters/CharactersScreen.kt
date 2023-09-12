@file:OptIn(ExperimentalMaterial3Api::class)

package com.theblackbit.rickandmorty.feature.character.presentation.screens.listcharacters

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.core.resources.R
import com.theblackbit.rickandmorty.feature.character.presentation.CharactersViewModel
import com.theblackbit.rickandmorty.feature.character.presentation.composables.ProgressComposable
import kotlin.math.absoluteValue

@Composable
fun Container(viewModel: CharactersViewModel = hiltViewModel()) {
    val characters = viewModel.collectCharacters(viewModel.viewModelScope).collectAsLazyPagingItems()
    val scrollState = rememberLazyStaggeredGridState()
    CharactersScreen(pagingItems = characters, scrollState = scrollState)
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun CharactersScreen(
    pagingItems: LazyPagingItems<Character>,
    scrollState: LazyStaggeredGridState
) {
    val context = LocalContext.current

    val value = remember {
        derivedStateOf {
             val size = scrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.height ?: 1
              val offset = scrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset?.y?.absoluteValue ?: 0
            if(scrollState.firstVisibleItemIndex == 0) offset / size.toFloat() else 1f
        }
    }

    val progress by animateFloatAsState(
        targetValue = value.value,
        tween(10), label = "Animation"
    )

    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Logo(
            modifier = Modifier
                .layoutId("headerImage")
                .padding(vertical = 20.dp)
                .width(300.dp)
        )

        Box(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .layoutId("content")
        ) {
            GridCharacters(pagingItems = pagingItems, scrollState)
        }
    }
}

@Composable
private fun Logo(
    modifier: Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo Rick And Morty"
    )
}

@Composable
private fun GridCharacters(
    pagingItems: LazyPagingItems<Character>,
    scrollState: LazyStaggeredGridState
) {
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
        state = scrollState,
        content = {
            items(count = pagingItems.itemCount) { index ->
                val item = pagingItems[index]
                item?.let {
                    CharacterCard(
                        character = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(it.height.dp)
                    )
                }
            }
        })

    if (pagingItems.loadState.refresh == LoadState.Loading) {
        ProgressComposable()
    }
}


@Composable
private fun CharacterCard(
    character: Character,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = modifier,
            onClick = { },
        ) {
            GlideImage(
                modifier = modifier,
                imageModel = character.image,
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colorScheme.onBackground,
                    highlightColor = Color.LightGray,
                    durationMillis = 2000,
                    dropOff = 0.65f,
                    tilt = 20f,
                ),
            )
        }

        Text(
            text = character.name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
