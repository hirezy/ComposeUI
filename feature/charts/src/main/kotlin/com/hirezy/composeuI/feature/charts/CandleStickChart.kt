package com.hirezy.composeuI.feature.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirezy.composeuI.feature.charts.model.CandleItem
import kotlin.collections.forEachIndexed

@Composable
fun CandleStickChart(
    dataList: List<CandleItem>,
    modifier: Modifier = Modifier,
    candleWidth: Float = 22f, //K线实体宽度
) {
    val textMeasurer = rememberTextMeasurer()
    if (dataList.isEmpty()) return

    // 计算全局最大最小值（Y轴范围）
    val allLow = dataList.minOf { it.low }
    val allHigh = dataList.maxOf { it.high }
    val yRange = allHigh - allLow

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 30.dp, start = 10.dp, end = 10.dp)
    ) {
        val canvasW = size.width
        val canvasH = size.height
        val itemCount = dataList.size
        val spacing = canvasW / itemCount //每个K线占的水平空间

        dataList.forEachIndexed { index, candle ->
            // 当前K线中心X坐标
            val centerX = index * spacing + spacing / 2

            // Y坐标转换：数值 -> canvas像素
            fun valueToY(value: Float): Float {
                val ratio = (allHigh - value) / yRange
                return ratio * canvasH
            }

            val yOpen = valueToY(candle.open)
            val yClose = valueToY(candle.close)
            val yHigh = valueToY(candle.high)
            val yLow = valueToY(candle.low)

            val isRise = candle.close > candle.open //上涨：绿空心K；下跌：红色实心K

            // 1. 绘制上下影线（竖线）
            drawLine(
                color = Color.DarkGray,
                start = Offset(centerX, yHigh),
                end = Offset(centerX, yLow),
                strokeWidth = 1.5f,
                cap = StrokeCap.Round
            )

            // 2. K线实体
            if (isRise) {
                // 上涨：空心绿色矩形，只描边
                drawRect(
                    color = Color(0xFF77EE77),
                    topLeft = Offset(centerX - candleWidth / 2, minOf(yOpen, yClose)),
                    size = androidx.compose.ui.geometry.Size(candleWidth, kotlin.math.abs(yClose - yOpen)),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
                )
            } else {
                // 下跌：红色实心矩形
                drawRect(
                    color = Color(0xFFE53935),
                    topLeft = Offset(centerX - candleWidth / 2, minOf(yOpen, yClose)),
                    size = androidx.compose.ui.geometry.Size(candleWidth, kotlin.math.abs(yClose - yOpen))
                )
            }

            // 3. X轴年份标签（隔几个才绘制，避免文字拥挤，和样图一致）
            if (index % 3 == 0) {
                val measureResult = textMeasurer.measure(
                    text = candle.timeLabel,
                    style = TextStyle(fontSize = 14.sp, color = Color.Black)
                )
                drawText(
                    measureResult,
                    topLeft = Offset(
                        centerX - measureResult.size.width / 2,
                        canvasH + 8f
                    )
                )
            }
        }

        // X轴底线
        drawLine(
            color = Color.Gray,
            start = Offset(0f, canvasH + 20f),
            end = Offset(canvasW, canvasH + 20f),
            strokeWidth = 1f
        )
    }
}
