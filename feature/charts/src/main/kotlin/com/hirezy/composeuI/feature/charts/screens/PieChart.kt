package com.hirezy.composeuI.feature.charts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirezy.composeuI.core.ui.components.button.ButtonType
import com.hirezy.composeuI.core.ui.components.button.WeButton
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.core.utils.format
import com.hirezy.composeuI.core.utils.randomInt
import com.hirezy.composeuI.core.utils.rememberToggleState
import com.hirezy.composeuI.feature.charts.DefaultChartLegend
import com.hirezy.composeuI.feature.charts.WePieChart
import com.hirezy.composeuI.feature.charts.model.ChartData
import com.hirezy.composeuI.feature.charts.model.LegendPosition

@Composable
fun PieChartScreen() {
    WeScreen(
        title = "PieChart",
        description = "饼图",
        containerColor = MaterialTheme.colorScheme.surface,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 饼图数据源，rememberSaveable 屏幕旋转保留数据
        val dataSource = rememberSaveable { mutableStateOf(buildData()) }
        // 环形宽度切换：0.dp实心饼 / 40.dp环形甜甜圈图
        val (ringWidth, toggleRingWidth) = rememberToggleState(
            defaultValue = 0.dp,
            reverseValue = 40.dp
        )
        // 图例显示隐藏开关
        val (showLegend, toggleLegend) = rememberToggleState(
            defaultValue = false,
            reverseValue = true
        )
        // 新增：圆角端头开关状态
        val (roundedCaps, toggleRoundedCaps) = rememberToggleState(
            defaultValue = false,
            reverseValue = true
        )
        // 段与段之间间隙，推荐1.5dp，观感柔和
        val segmentGap = 1.5.dp

        // ==========新增：图例位置状态，默认底部 ==========
        val legendPosition = rememberSaveable { mutableStateOf(LegendPosition.Bottom) }
        // 循环切换四个位置
        fun toggleLegendPosition() {
            legendPosition.value = when (legendPosition.value) {
                LegendPosition.Top -> LegendPosition.Bottom
                LegendPosition.Bottom -> LegendPosition.Start
                LegendPosition.Start -> LegendPosition.End
                LegendPosition.End -> LegendPosition.Top
            }
        }

        // ==========新增：饼图半径比例，默认0.8f ==========
        val pieRadiusRatio = rememberSaveable { mutableStateOf(0.8f) }
        fun toggleRadiusRatio() {
            // 多档切换：0.5 → 0.7 → 0.8 → 0.95 → 0.5
            pieRadiusRatio.value = when(pieRadiusRatio.value) {
                0.5f -> 0.7f
                0.7f -> 0.8f
                0.8f -> 0.95f
                0.95f -> 0.5f
                else -> 0.8f
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WePieChart(
                dataSource = dataSource.value,
                modifier = Modifier.fillMaxWidth(0.75f),
                ringWidth = ringWidth.value,
                formatter = {
                    it.format() + "个"
                },
                roundedCaps = roundedCaps.value,
                segmentGap = segmentGap,
                // ==========外部传入中心总和UI，total由组件回传给你 ==========
                // 切换ringWidth到环形模式自动显示，实心自动隐藏
                centerText = { total ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "总计", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = total.format(), fontSize = 22.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                legendPosition = legendPosition.value,
                pieRadiusRatio = pieRadiusRatio.value,
                legendMaxWidth = 160.dp,
                legendContent = { items ->
                    if (showLegend.value) {
                        DefaultChartLegend(items, modifier = Modifier.padding(top = 16.dp), maxLineItemCount = 2)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        WeButton(text = "更新数据") {
            dataSource.value = buildData()
        }
        WeButton(text = "切换类型", type = ButtonType.PLAIN) {
            toggleRingWidth()
        }
        WeButton(
            text = "${if (showLegend.value) "隐藏" else "显示"}图例",
            type = ButtonType.PLAIN
        ) {
            toggleLegend()
        }
        // 新增按钮控制圆角端头开启关闭
        WeButton(
            text = "${if (roundedCaps.value) "关闭" else "开启"}圆角端头",
            type = ButtonType.PLAIN
        ) {
            toggleRoundedCaps()
        }
        // ==========新增按钮：切换图例位置 ==========
        WeButton(
            text = "切换图例位置：${legendPosition.value.name}",
            type = ButtonType.PLAIN
        ) {
            toggleLegendPosition()
        }
        // ==========新增按钮：切换饼图半径比例 ==========
        WeButton(
            text = "切换饼图大小：${pieRadiusRatio.value}",
            type = ButtonType.PLAIN
        ) {
            toggleRadiusRatio()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

/**
 * 构建随机测试数据
 */
private fun buildData(): List<ChartData> {
    val allFruits = listOf("苹果", "香蕉", "樱桃", "西瓜", "草莓")
    return allFruits.map { ChartData(randomInt(1, 100).toFloat(), it) }
}
