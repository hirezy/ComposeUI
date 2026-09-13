package com.hirezy.composeuI.feature.qrcode.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.qrcode.screens.QrCodeGeneratorScreen
import com.hirezy.composeuI.feature.qrcode.screens.QrCodeScanScreen

fun NavGraphBuilder.addQrCodeGraph() {
    composable("qrcode_scanner") {
        QrCodeScanScreen()
    }
    composable("qrcode_generator") {
        QrCodeGeneratorScreen()
    }
}