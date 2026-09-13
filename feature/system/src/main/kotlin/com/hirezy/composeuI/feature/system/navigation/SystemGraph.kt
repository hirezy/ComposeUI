package com.hirezy.composeuI.feature.system.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.system.address.AddressFormScreen
import com.hirezy.composeuI.feature.system.screens.CalendarEventsScreen
import com.hirezy.composeuI.feature.system.screens.ClipboardScreen
import com.hirezy.composeuI.feature.system.screens.ContactsScreen
import com.hirezy.composeuI.feature.system.screens.DatabaseScreen
import com.hirezy.composeuI.feature.system.screens.DeviceInfoScreen
import com.hirezy.composeuI.feature.system.screens.DownloaderScreen
import com.hirezy.composeuI.feature.system.screens.InstalledAppsScreen
import com.hirezy.composeuI.feature.system.screens.KeyboardScreen
import com.hirezy.composeuI.feature.system.screens.NotificationScreen
import com.hirezy.composeuI.feature.system.screens.SmsScreen
import com.hirezy.composeuI.feature.system.screens.SystemStatusScreen

fun NavGraphBuilder.addSystemGraph(navController: NavController) {
    composable("device_info") {
        DeviceInfoScreen()
    }
    composable("system_status") {
        SystemStatusScreen()
    }
    composable("installed_apps") {
        InstalledAppsScreen()
    }
    composable("downloader") {
        DownloaderScreen()
    }
    composable("database") {
        DatabaseScreen { addressId ->
            navController.navigate(buildString {
                append("address_form")
                if (addressId != null) {
                    append("?id=${addressId}")
                }
            })
        }
    }
    composable("address_form?id={id}") {
        val id = it.arguments?.getString("id")?.toInt()
        AddressFormScreen(navController, id)
    }
    composable("clipboard") {
        ClipboardScreen()
    }
    composable("contacts") {
        ContactsScreen()
    }
    composable("sms") {
        SmsScreen()
    }
    composable("keyboard") {
        KeyboardScreen()
    }
    composable("calendar_events") {
        CalendarEventsScreen()
    }
    composable("notification") {
        NotificationScreen()
    }
}