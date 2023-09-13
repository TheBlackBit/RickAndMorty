package com.theblackbit.rickandmorty.feature.character.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun GlideImageComposable(
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier
) {
    GlideImage(
        imageModel = imageUrl,
        contentScale = contentScale,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colorScheme.onBackground,
            highlightColor = Color.LightGray,
            durationMillis = 2000,
            dropOff = 0.65f,
            tilt = 20f
        ),
        modifier = modifier
    )
}
