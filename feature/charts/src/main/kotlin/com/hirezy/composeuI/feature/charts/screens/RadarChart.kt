package com.hirezy.composeuI.feature.charts.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hirezy.composeuI.feature.charts.AxisLabelStyle
import com.hirezy.composeuI.feature.charts.GridLineStyle
import com.hirezy.composeuI.feature.charts.RadarChart
import com.hirezy.composeuI.feature.charts.RadarChartConfig
import com.hirezy.composeuI.feature.charts.RadarDataSet
import com.hirezy.composeuI.feature.charts.RadarFillBrush
import com.hirezy.composeuI.feature.charts.RadarFillType
import com.hirezy.composeuI.feature.charts.RadarLegend

@Composable
fun RadarChartScreen() {
    val labels = listOf("输出", "生存", "团战", "发育", "意识", "操作")

    val dataList = remember {
        mutableStateListOf(
            RadarDataSet(
                name = "角色A",
                values = listOf(75f, 62f, 88f, 70f, 82f, 90f),
                lineColor = Color(0xFF4285F4),
                fillBrush = RadarFillBrush(
                    type = RadarFillType.Radial,
                    radialColors = listOf(Color(0x554285F4), Color(0x104285F4))
                ),
                pointColor = Color(0xFF4285F4),
                showPoint = true,
                dashPattern = floatArrayOf(8f, 4f),
                visible = true
            ),
            RadarDataSet(
                name = "角色B",
                values = listOf(85f, 78f, 65f, 80f, 70f, 76f),
                lineColor = Color(0xFFEA4335),
                fillBrush = RadarFillBrush(
                    type = RadarFillType.Linear,
                    linearBrush = Brush.linearGradient(
                        colors = listOf(Color(0x44EA4335), Color(0x11EA4335)),
                        start = Offset(0f, 0f),
                        end = Offset(300f, 300f)
                    )
                ),
                pointColor = Color(0xFFEA4335),
                showPoint = true,
                dashPattern = null,
                visible = true
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "能力雷达图", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

        RadarChart(
            labels = labels,
            dataSets = dataList,
            size = 340.dp,
            config = RadarChartConfig(
                maxValue = 100f,
                ringCount = 4,
                startAngleDegree = -90f,
                innerRingRatio = 0f,
                useCircleGrid = false,
                gridBgColor = Color(0xFFF8F8F8),
                gridLineStyle = GridLineStyle(color = Color(0xFFCCCCCC), strokeWidth = 1f),
                scaleUnitSuffix = "%",
                dimOtherDatasetsWhenSelected = true,
                dimAlpha = 0.3f,
                enableGesture = true,
                minZoom = 0.4f,
                maxZoom = 2.5f,
                axisLabelStyle = AxisLabelStyle(textColor = Color(0xFF333333), textSize = 13f)
            )
        )

        RadarLegend(
            dataSets = dataList,
            onDataSetClick = { index ->
                dataList[index] = dataList[index].copy(visible = !dataList[index].visible)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadarChartScreenPreview() {
    RadarChartScreen()
}
