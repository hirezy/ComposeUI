package com.hirezy.composeuI.feature.charts.model

data class CandleItem(
    val timeLabel: String,  // X轴文字，如"1994"
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float
)
