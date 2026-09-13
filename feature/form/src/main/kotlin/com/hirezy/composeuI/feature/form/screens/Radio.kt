package com.hirezy.composeuI.feature.form.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hirezy.composeuI.core.ui.components.radio.RadioOption
import com.hirezy.composeuI.core.ui.components.radio.WeRadioGroup
import com.hirezy.composeuI.core.ui.components.screen.WeScreen

@Composable
fun RadioScreen() {
    WeScreen(title = "Radio", description = "单选框") {
        var value by remember { mutableStateOf("2") }

        WeRadioGroup(
            listOf(
                RadioOption(label = "cell standard", value = "1"),
                RadioOption(label = "cell standard", value = "2"),
                RadioOption(label = "cell standard", value = "3", disabled = true),
            ),
            value = value
        ) {
            value = it
        }
    }
}