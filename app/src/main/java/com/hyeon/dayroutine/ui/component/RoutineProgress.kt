package com.hyeon.dayroutine.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
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
            size = Size(width = size.width * progress, height = size.height)
        )
    }
}