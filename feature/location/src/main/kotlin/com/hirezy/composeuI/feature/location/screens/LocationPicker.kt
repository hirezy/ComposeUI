package com.hirezy.composeuI.feature.location.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hirezy.composeuI.core.ui.components.button.WeButton
import com.hirezy.composeuI.core.ui.components.cardlist.WeCardListItem
import com.hirezy.composeuI.core.ui.components.cardlist.cardList
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.feature.location.data.model.LocationItem
import com.hirezy.composeuI.feature.location.picker.rememberPickLocationLauncher

@Composable
fun LocationPickerScreen() {
    WeScreen(title = "LocationPicker", description = "选择位置", scrollEnabled = false) {
        var location by remember { mutableStateOf<LocationItem?>(null) }

        location?.apply {
            LazyColumn(modifier = Modifier.cardList()) {
                item {
                    WeCardListItem(label = "纬度", value = latLng.latitude.toString())
                    WeCardListItem(label = "经度", value = latLng.longitude.toString())
                    WeCardListItem(label = "位置名称", value = name)
                    WeCardListItem(label = "详细位置", value = address ?: "")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        val pickLocation = rememberPickLocationLauncher {
            location = it
        }
        WeButton(text = "选择位置") {
            pickLocation()
        }
    }
}