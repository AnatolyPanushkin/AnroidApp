package com.example.myapplication.ui.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.myapplication.R
import kotlinx.coroutines.delay

@Composable
fun Like(
    modifier: Modifier,
    like: MutableState<Boolean>
) {
    val heartColor = animateColorAsState(
        targetValue = if (like.value) Color("#F84A00".toColorInt()) else Color.White,
        animationSpec = tween(durationMillis = 200),
        label = "LikeColorAnimation"
    )

    val isFirstShown = remember { mutableStateOf(true) }
    val heartSize = remember { mutableStateOf(24.dp) }
    val heartSizeState = animateDpAsState(
        targetValue = heartSize.value,
        animationSpec = tween(durationMillis = 100),
        label = "HeartSizeAnimation"
    )

    LaunchedEffect(like.value) {
        if (isFirstShown.value) {
            isFirstShown.value = false
        } else {
            heartSize.value = 28.dp
            delay(100)
            heartSize.value = 24.dp
        }
    }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .size(44.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false)
                ) { like.value = !like.value }
        ) {
            drawCircle(
                color = Color.Black.copy(alpha = 0.12f),
                radius = 22.dp.toPx()
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.16f),
                radius = 21.5.dp.toPx(),
                style = Stroke(width = 1.dp.toPx())
            )
        }

        Icon(
            modifier = Modifier
                .size(heartSizeState.value)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.like),
            contentDescription = "like",
            tint = heartColor.value
        )
    }
}