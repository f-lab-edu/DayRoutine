package com.hyeon.dayroutine.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
private const val PROGRESS_BAR_RADIUS = 3f
@Composable
fun RoutineProgress(
    modifier: Modifier,
    progress: Float
) {
    val animateProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
        ),
        label = "progress"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
    ) {
        drawRoundRect(
            color = Color(0xFF33334a),
            cornerRadius = CornerRadius(PROGRESS_BAR_RADIUS)
        )

        drawRoundRect(
            color = Color(0xFF6c63ff),
            cornerRadius = CornerRadius(PROGRESS_BAR_RADIUS),
            size = Size(width = size.width * animateProgress, height = size.height)
        )
    }
}