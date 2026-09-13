package com.hirezy.composeuI.feature.feedback.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.feedback.screens.ActionSheetScreen
import com.hirezy.composeuI.feature.feedback.screens.ContextMenuScreen
import com.hirezy.composeuI.feature.feedback.screens.DialogScreen
import com.hirezy.composeuI.feature.feedback.screens.InformationBarScreen
import com.hirezy.composeuI.feature.feedback.screens.PopupScreen
import com.hirezy.composeuI.feature.feedback.screens.ToastScreen

fun NavGraphBuilder.addFeedbackGraph() {
    composable("dialog") {
        DialogScreen()
    }
    composable("popup") {
        PopupScreen()
    }
    composable("action_sheet") {
        ActionSheetScreen()
    }
    composable("toast") {
        ToastScreen()
    }
    composable("information_bar") {
        InformationBarScreen()
    }
    composable("context_menu") {
        ContextMenuScreen()
    }
}