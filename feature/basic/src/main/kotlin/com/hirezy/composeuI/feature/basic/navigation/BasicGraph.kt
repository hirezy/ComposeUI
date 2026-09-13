package com.hirezy.composeuI.feature.basic.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.basic.screens.BadgeScreen
import com.hirezy.composeuI.feature.basic.screens.LoadMoreScreen
import com.hirezy.composeuI.feature.basic.screens.LoadingScreen
import com.hirezy.composeuI.feature.basic.screens.ProgressScreen
import com.hirezy.composeuI.feature.basic.screens.RefreshViewScreen
import com.hirezy.composeuI.feature.basic.screens.SkeletonScreen
import com.hirezy.composeuI.feature.basic.screens.StepsScreen
import com.hirezy.composeuI.feature.basic.screens.SwipeActionScreen
import com.hirezy.composeuI.feature.basic.screens.SwiperScreen
import com.hirezy.composeuI.feature.basic.screens.TabViewScreen
import com.hirezy.composeuI.feature.basic.screens.TreeScreen

fun NavGraphBuilder.addBasicGraph() {
    composable("badge") {
        BadgeScreen()
    }
    composable("loading") {
        LoadingScreen()
    }
    composable("load_more") {
        LoadMoreScreen()
    }
    composable("progress") {
        ProgressScreen()
    }
    composable("steps") {
        StepsScreen()
    }
    composable("swiper") {
        SwiperScreen()
    }
    composable("refresh_view") {
        RefreshViewScreen()
    }
    composable("tab_view") {
        TabViewScreen()
    }
    composable("swipe_action") {
        SwipeActionScreen()
    }
    composable("skeleton") {
        SkeletonScreen()
    }
    composable("tree") {
        TreeScreen()
    }
}