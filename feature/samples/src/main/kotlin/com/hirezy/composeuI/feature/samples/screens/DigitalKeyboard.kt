package com.hirezy.composeuI.feature.samples.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hirezy.composeuI.core.ui.components.button.ButtonType
import com.hirezy.composeuI.core.ui.components.button.WeButton
import com.hirezy.composeuI.core.ui.components.input.WeInput
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.core.ui.theme.DangerColorLight
import com.hirezy.composeuI.core.ui.theme.WeUITheme
import com.hirezy.composeuI.core.utils.clickableWithoutRipple
import com.hirezy.composeuI.core.utils.rememberToggleState
import com.hirezy.composeuI.feature.samples.components.digitalkeyboard.DigitalKeyboardConfirmOptions
import com.hirezy.composeuI.feature.samples.components.digitalkeyboard.WeDigitalKeyboard

@Composable
fun DigitalKeyboardScreen() {
    WeScreen(title = "DigitalKeyboard", description = "数字键盘") {
        var value by remember { mutableStateOf("") }
        var visible by remember { mutableStateOf(true) }
        var allowDecimal by remember { mutableStateOf(true) }
        val (confirmButtonOptions, toggleConfirmButtonOptions) = rememberToggleState(
            defaultValue = DigitalKeyboardConfirmOptions(),
            reverseValue = DigitalKeyboardConfirmOptions(
                color = DangerColorLight,
                text = "转账"
            )
        )

        WeInput(
            value = value,
            label = "金额",
            placeholder = "请输入",
            disabled = true,
            modifier = Modifier.clickableWithoutRipple {
                visible = true
            }
        )

        if (visible) {
            Spacer(modifier = Modifier.height(40.dp))
            WeButton(
                text = "${if (allowDecimal) "不" else ""}允许小数点",
                type = ButtonType.PLAIN
            ) {
                value = ""
                allowDecimal = !allowDecimal
            }
            Spacer(modifier = Modifier.height(20.dp))
            WeButton(text = "切换样式") {
                toggleConfirmButtonOptions()
            }
        }

        WeDigitalKeyboard(
            visible,
            value = value,
            allowDecimal = allowDecimal,
            confirmButtonOptions = confirmButtonOptions.value,
            onHide = {
                visible = false
            },
            onConfirm = {}
        ) {
            value = it
        }
    }
}

@Preview
@Composable
private fun PreviewDigitalKeyboard() {
    WeUITheme {
        DigitalKeyboardScreen()
    }
}