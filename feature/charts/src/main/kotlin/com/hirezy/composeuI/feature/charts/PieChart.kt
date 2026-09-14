package com.hirezy.composeuI.feature.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirezy.composeuI.core.utils.generateColors
import com.hirezy.composeuI.feature.charts.model.ChartData
import com.hirezy.composeuI.feature.charts.model.LegendPosition
import com.hirezy.composeuI.feature.charts.model.PieChartLegendItem
import kotlinx.coroutines.launch

@Composable
fun WePieChart(
    dataSource: List<ChartData>,
    modifier: Modifier = Modifier,
    ringWidth: Dp = 0.dp,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 800),
    formatter: (Float) -> String = { it.toString() },
    // ========== 新增：圆角端头与相邻段微小间隙 ==========
    // 为了兼容旧样式，这两个参数都有默认值，不传时保持原有效果
    roundedCaps: Boolean = false,           // true：开启弧段两端圆润端头，仅环形图生效
    segmentGap: Dp = 0.dp,                  // 相邻段之间的微小间隙，建议 1.dp ~ 3.dp
    // ==========新增：中心自定义内容，外部传入，total为总和，null则不绘制 ==========
    centerText: (@Composable (total: Float) -> Unit)? = null,
    // ==========新增：图例位置，默认底部 ==========
    legendPosition: LegendPosition = LegendPosition.Bottom,
    // ==========新增：饼图半径占可用空间比例，0~1；解决左右图例时饼图过大挤压问题 ==========
    pieRadiusRatio: Float = 0.8f,
    // ==========新增：水平布局（左右图例）时图例最大宽度，防止图例占满屏幕 ==========
    legendMaxWidth: Dp = 160.dp,
    // ==========新增：图例一行最多展示条目数量，默认2个，限制横向拉长 ==========
    legendMaxLineItemCount: Int = 2,
    legendContent: @Composable ((List<PieChartLegendItem>) -> Unit)?,
) {
    require(pieRadiusRatio in 0.01f..1f) { "pieRadiusRatio must between 0.01 ~ 1.0" }
    require(legendMaxLineItemCount > 0) { "legendMaxLineItemCount must >0" }

    // 计算全部数据总和
    val total = remember(dataSource) { dataSource.sumOf { it.value.toDouble() }.toFloat() }
    // 根据数据条目数量生成配套颜色
    val colors = remember(dataSource.size) { generateColors(dataSource.size) }
    // 每一段扇区的动画角度控制器
    val animatedSweepAngles = remember(dataSource.size) { dataSource.map { Animatable(0f) } }

    // 计算图例所需的数据
    val legendItems = remember(dataSource, colors, total) {
        dataSource.mapIndexed { index, item ->
            PieChartLegendItem(
                label = item.label,
                value = item.value,
                color = item.color ?: colors[index],
                percentage = if (total > 0f) item.value / total else 0f,
                formattedValue = formatter(item.value)
            )
        }
    }

    // 入场动画：数据变更时，驱动每一段扇区角度动画
    LaunchedEffect(dataSource) {
        var accumulatedAngle = 0f
        dataSource.forEachIndexed { index, item ->
            val targetAngle = if (total > 0) (item.value / total * 360f) else 0f
            val endAngle = accumulatedAngle + targetAngle
            launch {
                animatedSweepAngles[index].animateTo(endAngle, animationSpec)
            }
            accumulatedAngle += targetAngle
        }
    }

    // ==========新增：根据图例位置选择布局方向 ==========
    // 垂直布局：Top / Bottom；水平布局：Start / End
    val isVerticalLayout = remember(legendPosition) {
        legendPosition == LegendPosition.Top || legendPosition == LegendPosition.Bottom
    }

    // 外层布局容器：自动切换行/列，实现图例上下左右摆放
    Box(modifier = modifier) {
        if (isVerticalLayout) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 图例在顶部：先渲染图例
                if (legendPosition == LegendPosition.Top) {
                    legendContent?.invoke(legendItems)
                }

                // 饼图主体
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f), // 上下布局场景，宽高1:1没问题
                    contentAlignment = Alignment.Center
                ) {
                    // 底层绘制饼图Canvas，传入半径比例
                    PieFace(
                        modifier = Modifier.matchParentSize(),
                        animatedSweepAngles = animatedSweepAngles,
                        legendItems = legendItems,
                        ringWidth = ringWidth,
                        roundedCaps = roundedCaps,
                        segmentGap = segmentGap,
                        pieRadiusRatio = pieRadiusRatio
                    )

                    // ==========新增：环形图才渲染中心文字，实心饼不显示 ==========
                    // ringWidth>0代表当前是环形甜甜圈图，实心饼图不需要中心文字
                    if (ringWidth.value > 0 && centerText != null) {
                        centerText(total)
                    }
                }

                // 图例在底部：饼图渲染完成后渲染图例（默认）
                if (legendPosition == LegendPosition.Bottom) {
                    legendContent?.invoke(legendItems)
                }
            }
        } else {
            // ==========水平布局（左右图例） ==========
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 图例在左侧，增加 widthIn 限制最大宽度，防止图例无限抢占宽度
                if (legendPosition == LegendPosition.Start) {
                    Box(modifier = Modifier.widthIn(max = legendMaxWidth)) {
                        legendContent?.invoke(legendItems)
                    }
                }

                // 饼图主体 weight(1f) 自动占用【剩余所有宽度】，图例被限制最大宽度，饼图一定有空间
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    PieFace(
                        modifier = Modifier.matchParentSize(),
                        animatedSweepAngles = animatedSweepAngles,
                        legendItems = legendItems,
                        ringWidth = ringWidth,
                        roundedCaps = roundedCaps,
                        segmentGap = segmentGap,
                        pieRadiusRatio = pieRadiusRatio
                    )

                    if (ringWidth.value > 0 && centerText != null) {
                        centerText(total)
                    }
                }

                // 图例在右侧，增加 widthIn 限制最大宽度
                if (legendPosition == LegendPosition.End) {
                    Box(modifier = Modifier.widthIn(max = legendMaxWidth)) {
                        legendContent?.invoke(legendItems)
                    }
                }
            }
        }
    }
}

@Composable
private fun PieFace(
    modifier: Modifier,
    animatedSweepAngles: List<Animatable<Float, AnimationVector1D>>,
    legendItems: List<PieChartLegendItem>,
    ringWidth: Dp,
    // 新增：接收圆角端头与相邻段间隙参数
    roundedCaps: Boolean,
    segmentGap: Dp,
    // ==========新增：饼图半径比例 ==========
    pieRadiusRatio: Float
) {
    Canvas(modifier = modifier) {
        val strokeWidthPx = ringWidth.toPx()
        val isDonut = strokeWidthPx > 0f // 环形图
        var startAngle = -90f

        // ========== 新增：根据间隙尺寸换算成角度 ==========
        // 说明：Canvas 里 drawArc 使用角度定位，所以不能直接用 dp 间隙，
        // 需要先把 segmentGap 转成像素，再根据圆环所在圆周换算成角度。
        val gapPx = segmentGap.toPx()
        val gapAngle = if (isDonut && roundedCaps && gapPx > 0f) {
            // 圆环中心线周长近似为 π * (外圈直径 - 环宽度)
            val circumference = (size.width - strokeWidthPx) * Math.PI.toFloat()
            if (circumference > 0f) {
                // 将像素间隙映射为角度，保证圆角端头不会互相挤压
                (gapPx / circumference) * 360f
            } else 0f
        } else 0f

        // ========== 计算可用正方形边长，取宽高最小值，再乘以半径比例 ==========
        val availableSize = minOf(size.width, size.height)
        val radius = availableSize * pieRadiusRatio / 2f
        val center = Offset(size.width / 2, size.height / 2)
        val pieSize = Size(radius * 2, radius * 2)
        val pieTopLeft = Offset(center.x - radius, center.y - radius)

        animatedSweepAngles.forEachIndexed { index, animatable ->
            val currentTotalAngle = animatable.value
            val previousTotalAngle = if (index > 0) animatedSweepAngles[index - 1].value else 0f
            val rawSweepAngle = currentTotalAngle - previousTotalAngle

            // ========== 新增：给每段弧扣除间隙角度 ==========
            // 说明：真正绘制的 sweepAngle 要减去 gapAngle，
            // 这样段与段之间才会留出肉眼可见的微小间隔。
            val sweepAngle = (rawSweepAngle - gapAngle).coerceAtLeast(0f)

            if (isDonut) {
                // ========== 新增：根据 roundedCaps 选择端头样式 ==========
                // StrokeCap.Round：弧段两端变成半圆端头，形成你要的圆弧接头效果
                // StrokeCap.Butt：保持原来的平直端点
                val strokeCap = if (roundedCaps) StrokeCap.Round else StrokeCap.Butt

                drawArc(
                    color = legendItems[index].color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = pieTopLeft,
                    size = pieSize,
                    style = Stroke(
                        width = strokeWidthPx,
                        cap = strokeCap // 新增：应用圆角端头配置
                    )
                )
            } else {
                // 实心饼图保持原有逻辑不变
                drawArc(
                    color = legendItems[index].color,
                    startAngle = startAngle,
                    sweepAngle = rawSweepAngle,
                    useCenter = true,
                    topLeft = pieTopLeft,
                    size = pieSize
                )
            }

            // ========== 新增：下一段起点跳过间隙 ==========
            // 说明：这里仍然按原始角度推进，而不是按扣除后的 sweepAngle 推进，
            // 目的是让动画进度和数据比例保持一致，同时把 gapAngle 作为段间留白。
            startAngle += rawSweepAngle + gapAngle
        }
    }
}

/**
 * 通用图例组件，增加 maxLineItemCount：一行最多显示多少个图例项
 */
@Composable
fun DefaultChartLegend(
    items: List<PieChartLegendItem>,
    modifier: Modifier = Modifier,
    maxLineItemCount: Int = 2
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = maxLineItemCount // 核心：一行最多N个，自动换行，不会无限横向拉伸
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(item.color, CircleShape)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${item.label} (${item.formattedValue})",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
