package com.hirezy.composeuI.feature.location.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.location.screens.LocationPickerScreen
import com.hirezy.composeuI.feature.location.screens.LocationPreviewScreen

fun NavGraphBuilder.addLocationGraph() {
    composable("location_preview") {
        LocationPreviewScreen()
    }
    composable("location_picker") {
        LocationPickerScreen()
    }
}