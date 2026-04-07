package com.hyeon.dayroutine.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

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
        val radius = size.height / 2

        drawRoundRect(
            color = Color(0xFF33334a),
            cornerRadius = CornerRadius(radius)
        )

        drawRoundRect(
            color = Color(0xFF6c63ff),
            cornerRadius = CornerRadius(radius),
            size = Size(width = size.width * progress, height = size.height)
        )
    }
}