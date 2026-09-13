package com.hirezy.composeuI.feature.system.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hirezy.composeuI.core.ui.components.button.ButtonType
import com.hirezy.composeuI.core.ui.components.button.WeButton
import com.hirezy.composeuI.core.ui.components.dialog.rememberDialogState
import com.hirezy.composeuI.core.ui.components.input.WeTextarea
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.core.ui.components.toast.ToastIcon
import com.hirezy.composeuI.core.ui.components.toast.rememberToastState
import com.hirezy.composeuI.core.utils.getClipboardData
import com.hirezy.composeuI.core.utils.setClipboardData

@Composable
fun ClipboardScreen() {
    WeScreen(title = "Clipboard", description = "剪贴板") {
        var data by remember { mutableStateOf("") }
        val context = LocalContext.current
        val dialog = rememberDialogState()
        val toast = rememberToastState()

        WeTextarea(data, placeholder = "请输入内容", max = 200, topBorder = true) {
            data = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        WeButton(text = "设置剪贴板内容") {
            if (data.isEmpty()) {
                toast.show("内容不能为空", ToastIcon.FAIL)
            } else {
                context.setClipboardData(data)
                toast.show("已复制", ToastIcon.SUCCESS)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        WeButton(text = "读取剪贴板内容", type = ButtonType.PLAIN) {
            context.getClipboardData()?.let {
                dialog.show(
                    title = "剪贴板内容",
                    content = it,
                    onCancel = null
                )
            } ?: toast.show("获取失败", ToastIcon.FAIL)
        }
    }
}