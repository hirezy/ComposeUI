package com.hirezy.composeuI.feature.form.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hirezy.composeuI.core.ui.components.checkbox.CheckboxOption
import com.hirezy.composeuI.core.ui.components.checkbox.WeCheckboxGroup
import com.hirezy.composeuI.core.ui.components.screen.WeScreen

@Composable
fun CheckboxScreen() {
    WeScreen(title = "Checkbox", description = "复选框") {
        var values by remember { mutableStateOf<List<Int>>(emptyList()) }

        WeCheckboxGroup(
            listOf(
                CheckboxOption(label = "standard is dealt for u.", value = 1),
                CheckboxOption(label = "standard is dealicient for u.", value = 2),
                CheckboxOption(label = "standard is dealicient for u.", value = 3, disabled = true)
            ),
            values
        ) {
            values = it
        }
    }
}