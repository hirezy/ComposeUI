package com.hirezy.composeuI.feature.samples.paint

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class StrokeItem(
    val path: Path,
    val color: Color,
    val width: Float
)