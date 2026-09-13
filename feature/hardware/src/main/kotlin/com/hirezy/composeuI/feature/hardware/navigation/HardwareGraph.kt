package com.hirezy.composeuI.feature.hardware.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.hardware.screens.AccelerometerScreen
import com.hirezy.composeuI.feature.hardware.screens.BluetoothScreen
import com.hirezy.composeuI.feature.hardware.screens.CompassScreen
import com.hirezy.composeuI.feature.hardware.screens.FingerprintScreen
import com.hirezy.composeuI.feature.hardware.screens.FlashlightScreen
import com.hirezy.composeuI.feature.hardware.screens.GNSSScreen
import com.hirezy.composeuI.feature.hardware.screens.GyroscopeScreen
import com.hirezy.composeuI.feature.hardware.screens.HygrothermographScreen
import com.hirezy.composeuI.feature.hardware.screens.InfraredScreen
import com.hirezy.composeuI.feature.hardware.screens.ScreenScreen
import com.hirezy.composeuI.feature.hardware.screens.VibrationScreen
import com.hirezy.composeuI.feature.hardware.screens.WiFiScreen

fun NavGraphBuilder.addHardwareGraph() {
    composable("screen") {
        ScreenScreen()
    }
    composable("flashlight") {
        FlashlightScreen()
    }
    composable("vibration") {
        VibrationScreen()
    }
    composable("wifi") {
        WiFiScreen()
    }
    composable("bluetooth") {
        BluetoothScreen()
    }
    composable("gnss") {
        GNSSScreen()
    }
    composable("infrared") {
        InfraredScreen()
    }
    composable("gyroscope") {
        GyroscopeScreen()
    }
    composable("compass") {
        CompassScreen()
    }
    composable("accelerometer") {
        AccelerometerScreen()
    }
    composable("hygrothermograph") {
        HygrothermographScreen()
    }
    composable("fingerprint") {
        FingerprintScreen()
    }
}