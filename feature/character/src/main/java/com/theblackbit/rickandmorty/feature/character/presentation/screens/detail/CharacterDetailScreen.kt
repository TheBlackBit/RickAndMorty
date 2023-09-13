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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.core.model.genderIcon
import com.theblackbit.rickandmorty.core.model.statusIcon
import com.theblackbit.rickandmorty.core.resources.R
import com.theblackbit.rickandmorty.feature.character.presentation.composables.BackIconComposable
import com.theblackbit.rickandmorty.feature.character.presentation.composables.GlideImageComposable
import com.theblackbit.rickandmorty.feature.character.presentation.composables.LottieImageComposable
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

        GlideImageComposable(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .constrainAs(avatar) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(content.top)
                    bottom.linkTo(content.top)
                }
        )

        BackIconComposable(
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
    val showMainInfo = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(300)
        showMainInfo.value = true
        delay(100)
    }

    Column(
        modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(
            modifier = Modifier.padding(top = 100.dp),
            visible = showMainInfo.value,
            enter = slideInHorizontally(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            ContentDetailCharacterComposable(character = character)
        }
    }
}

@Composable
private fun ContentDetailCharacterComposable(character: Character?) {
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
        GenderComposable(character = character)
        StatusComposable(character = character)

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LottieImageComposable(icon = character.statusIcon())
        }
    }
}

@Composable
private fun StatusComposable(character: Character?) {
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
}

@Composable
private fun GenderComposable(character: Character?) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = character.genderIcon()),
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
