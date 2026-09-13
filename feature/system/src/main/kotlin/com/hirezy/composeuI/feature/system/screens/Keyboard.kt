package com.hirezy.composeuI.feature.system.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.hirezy.composeuI.core.ui.components.button.ButtonType
import com.hirezy.composeuI.core.ui.components.button.WeButton
import com.hirezy.composeuI.core.ui.components.input.WeInput
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.core.utils.format
import com.hirezy.composeuI.core.utils.rememberKeyboardHeight

@Composable
fun KeyboardScreen() {
    WeScreen(title = "Keyboard", description = "键盘") {
        val keyboardController = LocalSoftwareKeyboardController.current
        val keyboardHeight = rememberKeyboardHeight()

        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        WeInput(
            value = null,
            placeholder = "键盘高度: ${keyboardHeight.value.format()}(dp)",
            modifier = Modifier.focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.height(40.dp))
        WeButton(text = "弹出键盘") {
            keyboardController?.show()
        }
        Spacer(modifier = Modifier.height(20.dp))
        WeButton(text = "收起键盘", type = ButtonType.PLAIN) {
            keyboardController?.hide()
        }
    }
}