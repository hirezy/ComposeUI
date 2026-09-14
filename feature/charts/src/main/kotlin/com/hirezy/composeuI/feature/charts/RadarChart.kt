package com.hirezy.composeuI.feature.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

//region 数据模型
enum class RadarFillType {
    Linear, Radial
}

data class AxisLabelStyle(
    val textColor: Color = Color.DarkGray,
    val textSize: Float = 12f
)

data class GridLineStyle(
    val color: Color = Color.LightGray,
    val strokeWidth: Float = 1f
)

data class RadarFillBrush(
    val type: RadarFillType = RadarFillType.Linear,
    val linearBrush: Brush? = null,
    val radialColors: List<Color> = emptyList()
)

data class RadarDataSet(
    val name: String,
    val values: List<Float>,
    val lineColor: Color,
    val fillBrush: RadarFillBrush,
    val pointColor: Color,
    val pointRadius: Float = 4f,
    val lineWidth: Float = 2.5f,
    val showPoint: Boolean = true,
    val dashPattern: FloatArray? = null,
    var visible: Boolean = true
)

data class RadarChartConfig(
    val maxValue: Float = 100f,
    val ringCount: Int = 4,
    val startAngleDegree: Float = -90f,
    val innerRingRatio: Float = 0f,
    val useCircleGrid: Boolean = false,
    val gridBgColor: Color? = null,
    val gridLineStyle: GridLineStyle = GridLineStyle(),
    val showGrid: Boolean = true,
    val showAxisLine: Boolean = true,
    val showGridLabel: Boolean = true,
    val showScaleText: Boolean = true,
    val scaleUnitSuffix: String = "",
    val showPointValueLabel: Boolean = true,
    val showSelectedAxisHighlight: Boolean = true,
    val dimOtherDatasetsWhenSelected: Boolean = true,
    val dimAlpha: Float = 0.35f,
    val enableAnimation: Boolean = true,
    val animationSpec: AnimationSpec<Float> = tween(800),
    val enableGesture: Boolean = true,
    val minZoom: Float = 0.4f,
    val maxZoom: Float = 2.5f,
    val axisColor: Color = Color.LightGray,
    val scaleTextColor: Color = Color.Gray,
    val scaleTextSize: Float = 11f,
    val axisLabelStyle: AxisLabelStyle = AxisLabelStyle(),
    val pointValueTextSize: Float = 10f,
    val emptyHintText: String = "暂无数据",
)
//endregion

//region 图例、Tooltip
@Composable
fun RadarLegend(
    dataSets: List<RadarDataSet>,
    modifier: Modifier = Modifier,
    onDataSetClick: (index: Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dataSets.forEachIndexed { index, ds ->
            Row(
                modifier = Modifier
                    .clickable(role = Role.Button) { onDataSetClick(index) }
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(
                        color = if (ds.visible) ds.lineColor else Color.Gray.copy(alpha = 0.3f),
                        radius = 6f
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = ds.name,
                    fontSize = 12.sp,
                    color = if (ds.visible) Color.DarkGray else Color.Gray.copy(alpha = 0.4f)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun RadarTooltip(
    text: String,
    anchor: Offset,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(top = anchor.y.dp + 8.dp, start = anchor.x.dp)
    ) {
        Surface(
            modifier = Modifier.padding(4.dp),
            shape = RoundedCornerShape(4.dp),
            color = Color(0xCC222222),
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}
//endregion

//region 核心雷达图（终极修复版）
@Composable
fun RadarChart(
    labels: List<String>,
    dataSets: List<RadarDataSet>,
    modifier: Modifier = Modifier,
    size: Dp = 300.dp,
    config: RadarChartConfig = RadarChartConfig(),
    onCaptureBitmap: ((ImageBitmap) -> Unit)? = null
) {
    require(labels.isNotEmpty()) { "维度标签不能为空" }
    require(config.innerRingRatio in 0f..0.9f) { "innerRingRatio 取值范围0~0.9" }

    val textMeasurer = rememberTextMeasurer()
    val dimensionCount = labels.size
    val startAngleRad = Math.toRadians(config.startAngleDegree.toDouble())
    val density = LocalDensity.current

    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(dataSets) {
        if (config.enableAnimation) {
            animProgress.animateTo(1f, animationSpec = config.animationSpec)
        } else {
            animProgress.snapTo(1f)
        }
    }

    var zoom by remember { mutableStateOf(1f) }
    var panOffset by remember { mutableStateOf(Offset.Zero) }
    var selectedPointInfo by remember { mutableStateOf<Triple<RadarDataSet, Int, Offset>?>(null) }
    var tooltipMsg by remember { mutableStateOf<String?>(null) }
    var touchAnchor by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(
                modifier = Modifier
                    .size(size)
                    .pointerInput(config.enableGesture) {
                        if (config.enableGesture) {
                            detectTransformGestures { _, pan, gestureZoom, _ ->
                                var newZoom = zoom * gestureZoom
                                newZoom = newZoom.coerceIn(config.minZoom, config.maxZoom)
                                zoom = newZoom
                                panOffset += pan
                            }
                        }
                    }
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                val touchPos = event.changes.first().position
                                touchAnchor = touchPos
                                var hitInfo: String? = null
                                var hitTriple: Triple<RadarDataSet, Int, Offset>? = null

                                dataSets.filter { it.visible }.forEach { ds ->
                                    repeat(dimensionCount) { index ->
                                        val angle = startAngleRad + 2 * Math.PI * index / dimensionCount
                                        val scale = (ds.values[index] / config.maxValue) * animProgress.value
                                        val baseRadius = with(density) { size.toPx() } / 2 * 0.75f
                                        val pointR = baseRadius * scale
                                        val centerX = with(density) { size.toPx() } / 2 + panOffset.x
                                        val centerY = with(density) { size.toPx() } / 2 + panOffset.y
                                        val px = centerX + pointR * zoom * cos(angle).toFloat()
                                        val py = centerY + pointR * zoom * sin(angle).toFloat()
                                        val dist = hypot(touchPos.x - px, touchPos.y - py)
                                        if (dist < ds.pointRadius + 12f) {
                                            hitInfo = "${ds.name} | ${labels[index]}: ${ds.values[index]}${config.scaleUnitSuffix}"
                                            hitTriple = Triple(ds, index, Offset(px, py))
                                        }
                                    }
                                }
                                tooltipMsg = hitInfo
                                selectedPointInfo = hitTriple
                                if (hitTriple == null) {
                                    selectedPointInfo = null
                                    tooltipMsg = null
                                }
                            }
                        }
                    }
            ) {
                val canvasSize = this.size
                val centerX = canvasSize.width / 2 + panOffset.x
                val centerY = canvasSize.height / 2 + panOffset.y
                val baseRadius = minOf(canvasSize.width / 2, canvasSize.height / 2) * 0.75f
                val radius = baseRadius * zoom
                val innerRadius = radius * config.innerRingRatio

                val hasValidData = dataSets.any { it.visible && it.values.any { v -> v > 0f } }
                if (!hasValidData) {
                    val measureResult = textMeasurer.measure(
                        config.emptyHintText,
                        TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                    drawText(
                        measureResult,
                        topLeft = Offset(centerX - measureResult.size.width / 2, centerY - measureResult.size.height / 2)
                    )
                    return@Canvas
                }

                // 绘制背景
                config.gridBgColor?.let { bgColor ->
                    if (config.useCircleGrid) {
                        if (config.innerRingRatio > 0) {
                            drawCircle(color = bgColor, radius = radius, center = Offset(centerX, centerY))
                            drawCircle(color = Color.Transparent, radius = innerRadius, center = Offset(centerX, centerY))
                        } else {
                            drawCircle(color = bgColor, radius = radius, center = Offset(centerX, centerY))
                        }
                    } else {
                        val outerPath = Path()
                        repeat(dimensionCount) { index ->
                            val angle = startAngleRad + 2 * Math.PI * index / dimensionCount
                            val x = centerX + radius * cos(angle).toFloat()
                            val y = centerY + radius * sin(angle).toFloat()
                            if (index == 0) outerPath.moveTo(x, y) else outerPath.lineTo(x, y)
                        }
                        outerPath.close()

                        if (config.innerRingRatio > 0f) {
                            val innerPath = Path()
                            repeat(dimensionCount) { index ->
                                val angle = startAngleRad + 2 * Math.PI * index / dimensionCount
                                val x = centerX + innerRadius * cos(angle).toFloat()
                                val y = centerY + innerRadius * sin(angle).toFloat()
                                if (index == 0) innerPath.moveTo(x, y) else innerPath.lineTo(x, y)
                            }
                            innerPath.close()
                            val hollowPath = Path.combine(PathOperation.Difference, outerPath, innerPath)
                            drawPath(path = hollowPath, color = bgColor)
                        } else {
                            drawPath(path = outerPath, color = bgColor)
                        }
                    }
                }

                // 绘制网格 + 【终极修复格式化崩溃】
                if (config.showGrid) {
                    repeat(config.ringCount) { ringIndex ->
                        val ringPercent = (ringIndex + 1).toFloat() / config.ringCount
                        val ringR = radius * ringPercent
                        if (config.useCircleGrid) {
                            drawCircle(
                                color = config.gridLineStyle.color,
                                radius = ringR,
                                center = Offset(centerX, centerY),
                                style = Stroke(width = config.gridLineStyle.strokeWidth)
                            )
                        } else {
                            val path = Path()
                            repeat(dimensionCount) { index ->
                                val angle = startAngleRad + 2 * Math.PI * index / dimensionCount
                                val x = centerX + ringR * cos(angle).toFloat()
                                val y = centerY + ringR * sin(angle).toFloat()
                                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                            }
                            path.close()
                            drawPath(path, color = config.gridLineStyle.color, style = Stroke(width = config.gridLineStyle.strokeWidth))
                        }

                        // ========== 终极修复：防止 % 后缀闪退 ==========
                        if (config.showScaleText) {
                            val scaleValue = config.maxValue * ringPercent
                            val txt = buildString {
                                append("%.0f".format(scaleValue))
                                append(config.scaleUnitSuffix)
                            }
                            val tr = textMeasurer.measure(
                                txt,
                                style = TextStyle(fontSize = config.scaleTextSize.sp, color = config.scaleTextColor)
                            )
                            drawText(tr, topLeft = Offset(centerX - ringR - 12f, centerY - tr.size.height / 2))
                        }
                    }
                }

                // 绘制维度轴线、文字
                repeat(dimensionCount) { index ->
                    val angle = startAngleRad + 2 * Math.PI * index / dimensionCount
                    val x = centerX + radius * cos(angle).toFloat()
                    val y = centerY + radius * sin(angle).toFloat()
                    if (config.showAxisLine) {
                        drawLine(
                            color = config.axisColor,
                            start = Offset(centerX, centerY),
                            end = Offset(x, y),
                            strokeWidth = 1f
                        )
                    }
                    if (config.showGridLabel) {
                        val tr = textMeasurer.measure(
                            labels[index],
                            style = TextStyle(
                                fontSize = config.axisLabelStyle.textSize.sp,
                                color = config.axisLabelStyle.textColor
                            )
                        )
                        drawText(tr, topLeft = Offset(x + 8f, y - tr.size.height / 2))
                    }
                }

                // 选中轴线高亮
                if (config.showSelectedAxisHighlight && selectedPointInfo != null) {
                    val pointInfo = selectedPointInfo
                    val selectedPoint = pointInfo!!.third
                    drawLine(
                        color = Color.Yellow,
                        start = Offset(centerX, centerY),
                        end = selectedPoint,
                        strokeWidth = 3f
                    )
                }

                // 绘制数据集
                dataSets.filter { it.visible }.forEach { ds ->
                    var alpha = 1f
                    if (config.dimOtherDatasetsWhenSelected && selectedPointInfo != null) {
                        val selectedDs = selectedPointInfo!!.first
                        if (ds != selectedDs) alpha = config.dimAlpha
                    }

                    val points = mutableListOf<Offset>()
                    val path = Path()
                    repeat(dimensionCount) { index ->
                        val angle = startAngleRad + 2 * Math.PI * index / dimensionCount
                        val scale = (ds.values[index] / config.maxValue) * animProgress.value
                        val pr = radius * scale
                        val px = centerX + pr * cos(angle).toFloat()
                        val py = centerY + pr * sin(angle).toFloat()
                        points.add(Offset(px, py))
                        if (index == 0) path.moveTo(px, py) else path.lineTo(px, py)
                    }
                    path.close()

                    // 填充
                    when (ds.fillBrush.type) {
                        RadarFillType.Linear -> {
                            ds.fillBrush.linearBrush?.let {
                                drawPath(path, brush = it, alpha = alpha)
                            }
                        }
                        RadarFillType.Radial -> {
                            if (ds.fillBrush.radialColors.isNotEmpty()) {
                                val radialBrush = Brush.radialGradient(
                                    colors = ds.fillBrush.radialColors,
                                    center = Offset(centerX, centerY),
                                    radius = radius
                                )
                                drawPath(path, brush = radialBrush, alpha = alpha)
                            }
                        }
                    }

                    // 折线
                    val strokeEffect = ds.dashPattern?.let { PathEffect.dashPathEffect(it) }
                    drawPath(
                        path,
                        color = ds.lineColor.copy(alpha = alpha),
                        style = Stroke(
                            width = ds.lineWidth,
                            cap = StrokeCap.Round,
                            pathEffect = strokeEffect
                        )
                    )

                    // 拐点文字、圆点
                    if (ds.showPoint) {
                        points.forEachIndexed { idx, point ->
                            val isSelected = selectedPointInfo?.let {
                                it.first == ds && it.second == idx
                            } ?: false
                            val r = if (isSelected) ds.pointRadius * 1.6f else ds.pointRadius
                            val drawColor = if (isSelected) Color.Yellow else ds.pointColor.copy(alpha = alpha)
                            drawCircle(color = drawColor, radius = r, center = point)

                            if (config.showPointValueLabel) {
                                // ========== 终极修复：拐点文字 % 后缀闪退 ==========
                                val valueTxt = buildString {
                                    append("%.0f".format(ds.values[idx]))
                                    append(config.scaleUnitSuffix)
                                }
                                val tr = textMeasurer.measure(
                                    valueTxt,
                                    style = TextStyle(fontSize = config.pointValueTextSize.sp, color = ds.lineColor.copy(alpha = alpha))
                                )
                                drawText(tr, topLeft = Offset(point.x + 6f, point.y - 8f))
                            }
                        }
                    }
                }
            }

            Row {
                if (config.enableGesture) {
                    Button(
                        onClick = {
                            zoom = 1f
                            panOffset = Offset.Zero
                            selectedPointInfo = null
                            tooltipMsg = null
                        },
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                    ) {
                        Text("重置视图")
                    }
                }
                if (onCaptureBitmap != null) {
                    Button(
                        onClick = {},
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("导出图片")
                    }
                }
            }
        }

        tooltipMsg?.let {
            RadarTooltip(text = it, anchor = touchAnchor, modifier = Modifier.align(Alignment.TopStart))
        }
    }
}
//endregion
