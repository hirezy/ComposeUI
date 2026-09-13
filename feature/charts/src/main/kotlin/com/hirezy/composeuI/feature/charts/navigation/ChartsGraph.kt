package com.hirezy.composeuI.feature.charts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.charts.screens.BarChartScreen
import com.hirezy.composeuI.feature.charts.screens.LineChartScreen
import com.hirezy.composeuI.feature.charts.screens.PieChartScreen

fun NavGraphBuilder.addChartGraph() {
    composable("bar_chart") {
        BarChartScreen()
    }
    composable("line_chart") {
        LineChartScreen()
    }
    composable("pie_chart") {
        PieChartScreen()
    }
}