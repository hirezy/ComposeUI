package com.hirezy.composeuI.feature.samples.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.samples.filebrowser.FileBrowserScreen
import com.hirezy.composeuI.feature.samples.paint.PaintScreen
import com.hirezy.composeuI.feature.samples.screens.CalendarScreen
import com.hirezy.composeuI.feature.samples.screens.ClockScreen
import com.hirezy.composeuI.feature.samples.screens.CubicBezierScreen
import com.hirezy.composeuI.feature.samples.screens.DigitalKeyboardScreen
import com.hirezy.composeuI.feature.samples.screens.DigitalRollerScreen
import com.hirezy.composeuI.feature.samples.screens.DividingRuleScreen
import com.hirezy.composeuI.feature.samples.screens.DropCardScreen
import com.hirezy.composeuI.feature.samples.screens.IndexedListScreen
import com.hirezy.composeuI.feature.samples.screens.NotificationBarScreen
import com.hirezy.composeuI.feature.samples.screens.OrgTreeScreen
import com.hirezy.composeuI.feature.samples.screens.ReorderableScreen
import com.hirezy.composeuI.feature.samples.screens.SearchBarScreen
import com.hirezy.composeuI.feature.samples.screens.SolarSystemScreen
import com.hirezy.composeuI.feature.samples.videochannel.VideoChannelScreen

fun NavGraphBuilder.addSamplesGraph() {
    composable("search_bar") {
        SearchBarScreen()
    }
    composable("calendar") {
        CalendarScreen()
    }
    composable("clock") {
        ClockScreen()
    }
    composable("drop_card") {
        DropCardScreen()
    }
    composable("file_browser") {
        FileBrowserScreen()
    }
    composable("paint") {
        PaintScreen()
    }
    composable("indexed_list") {
        IndexedListScreen()
    }
    composable("reorderable") {
        ReorderableScreen()
    }
    composable("dividing_rule") {
        DividingRuleScreen()
    }
    composable("org_tree") {
        OrgTreeScreen()
    }
    composable("digital_roller") {
        DigitalRollerScreen()
    }
    composable("digital_keyboard") {
        DigitalKeyboardScreen()
    }
    composable("cubic_bezier") {
        CubicBezierScreen()
    }
    composable("notification_bar") {
        NotificationBarScreen()
    }
    composable("video_channel") {
        VideoChannelScreen()
    }
    composable("solar_system") {
        SolarSystemScreen()
    }
}