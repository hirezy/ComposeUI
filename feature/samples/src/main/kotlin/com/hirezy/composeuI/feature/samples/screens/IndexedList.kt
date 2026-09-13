package com.hirezy.composeuI.feature.samples.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.feature.samples.components.indexedlist.WeIndexedList
import com.hirezy.composeuI.feature.samples.data.CityDataProvider

@Composable
fun IndexedListScreen() {
    WeScreen(
        title = "IndexedList",
        description = "索引列表",
        padding = PaddingValues(0.dp),
        scrollEnabled = false
    ) {
        WeIndexedList(CityDataProvider.cities)
    }
}