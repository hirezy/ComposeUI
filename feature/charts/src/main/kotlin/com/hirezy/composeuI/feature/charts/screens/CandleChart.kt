package com.hirezy.composeuI.feature.charts.screens

import com.hirezy.composeuI.feature.charts.CandleStickChart
import com.hirezy.composeuI.feature.charts.model.CandleItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CandleChartScreen() {
    // 模拟样图里的时间序列数据，从1994开始
    val candleData = listOf(
        CandleItem("1994", open = 28f, high = 42f, low = 12f, close = 34f),
        CandleItem("1996", open = 32f, high = 45f, low = 16f, close = 24f),
        CandleItem("1998", open = 22f, high = 48f, low = 10f, close = 30f),
        CandleItem("2000", open = 26f, high = 40f, low = 8f, close = 14f),
        CandleItem("2002", open = 16f, high = 36f, low = 6f, close = 22f),
        CandleItem("2004", open = 24f, high = 44f, low = 14f, close = 32f),
        CandleItem("2006", open = 30f, high = 46f, low = 18f, close = 20f),
        CandleItem("2008", open = 20f, high = 41f, low = 9f, close = 33f),
        CandleItem("2010", open = 34f, high = 47f, low = 13f, close = 17f),
        CandleItem("2012", open = 18f, high = 43f, low = 11f, close = 31f),
        CandleItem("2014", open = 32f, high = 45f, low = 15f, close = 21f),
        CandleItem("2016", open = 23f, high = 42f, low = 10f, close = 33f),
        CandleItem("2018", open = 33f, high = 49f, low = 19f, close = 24f),
        CandleItem("2020", open = 25f, high = 44f, low = 12f, close = 36f),
        CandleItem("2022", open = 37f, high = 46f, low = 7f, close = 18f),
        CandleItem("2024", open = 17f, high = 40f, low = 8f, close = 30f),
        CandleItem("2026", open = 31f, high = 43f, low = 11f, close = 22f),
        CandleItem("2028", open = 21f, high = 42f, low = 9f, close = 32f),
        CandleItem("2030", open = 33f, high = 47f, low = 14f, close = 23f),
        CandleItem("2032", open = 24f, high = 44f, low = 10f, close = 34f),
        CandleItem("2034", open = 35f, high = 46f, low = 6f, close = 16f),
        CandleItem("2036", open = 18f, high = 41f, low = 7f, close = 31f),
        CandleItem("2038", open = 32f, high = 45f, low = 12f, close = 20f),
        CandleItem("2040", open = 22f, high = 43f, low = 13f, close = 33f),
        CandleItem("2042", open = 34f, high = 44f, low = 15f, close = 25f),
        CandleItem("2044", open = 26f, high = 40f, low = 11f, close = 36f),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "CandleStickChart (for financial data)",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        CandleStickChart(
            dataList = candleData,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCandleChart() {
    CandleChartScreen()
}
