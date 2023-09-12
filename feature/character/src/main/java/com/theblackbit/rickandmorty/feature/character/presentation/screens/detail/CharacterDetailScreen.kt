package com.theblackbit.rickandmorty.feature.character.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.core.model.isAlive
import com.theblackbit.rickandmorty.core.model.isMale
import com.theblackbit.rickandmorty.core.resources.R
import kotlinx.coroutines.delay

@Composable
fun CharacterDetailScreen(
    character: Character?,
    imageUrl: String,
    onBackPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (background, avatar, content, navigationIcon) = createRefs()


        Image(
            painter = painterResource(R.drawable.portal),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(background) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        Content(
            character = character,
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 350.dp)
                }
        )

        GlideImage(
            imageModel = imageUrl,
            contentScale = ContentScale.Crop,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colorScheme.onBackground,
                highlightColor = Color.LightGray,
                durationMillis = 2000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .constrainAs(avatar) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(content.top)
                    bottom.linkTo(content.top)
                }
        )

        BackIcon(
            onBackPressed = onBackPressed,
            modifier = Modifier.constrainAs(
                navigationIcon
            ) {
                start.linkTo(parent.start)
                top.linkTo(parent.top, 50.dp)
            }
        )
    }
}

@Composable
private fun Content(modifier: Modifier, character: Character?) {
    val iconAlive = if (character?.isAlive() == true) R.raw.alive else R.raw.dead
    val iconGender =
        if (character?.isMale() == true) R.drawable.baseline_male_24 else R.drawable.baseline_female_24

    val showGenderInfo = remember {
        mutableStateOf(false)
    }

    val showMainInfo = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(300)
        showMainInfo.value = true
        delay(100)
        showGenderInfo.value = true
    }

    Column(
        modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(
            visible = showGenderInfo.value,
            enter = slideInHorizontally(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 220.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconGender),
                    contentDescription = "Gender",
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = character?.gender ?: "",
                    fontWeight = FontWeight.W800,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.padding(top = 60.dp),
            visible = showMainInfo.value,
            enter = slideInHorizontally(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Column {
                Text(
                    text = character?.species ?: "",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.W800,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = character?.name ?: "",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W800,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.status),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.W800,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    Text(
                        text = character?.status ?: "",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.W800,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            iconAlive
                        )
                    )
                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BackIcon(
    onBackPressed: () -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.background, shape = CircleShape)
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}
