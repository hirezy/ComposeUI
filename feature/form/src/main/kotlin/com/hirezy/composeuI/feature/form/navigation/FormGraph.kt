package com.hirezy.composeuI.feature.form.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.form.screens.ButtonScreen
import com.hirezy.composeuI.feature.form.screens.CheckboxScreen
import com.hirezy.composeuI.feature.form.screens.InputScreen
import com.hirezy.composeuI.feature.form.screens.PickerScreen
import com.hirezy.composeuI.feature.form.screens.RadioScreen
import com.hirezy.composeuI.feature.form.screens.RateScreen
import com.hirezy.composeuI.feature.form.screens.SliderScreen
import com.hirezy.composeuI.feature.form.screens.SwitchScreen

fun NavGraphBuilder.addFormGraph() {
    composable("button") {
        ButtonScreen()
    }
    composable("checkbox") {
        CheckboxScreen()
    }
    composable("radio") {
        RadioScreen()
    }
    composable("switch") {
        SwitchScreen()
    }
    composable("slider") {
        SliderScreen()
    }
    composable("picker") {
        PickerScreen()
    }
    composable("input") {
        InputScreen()
    }
    composable("Rate") {
        RateScreen()
    }
}