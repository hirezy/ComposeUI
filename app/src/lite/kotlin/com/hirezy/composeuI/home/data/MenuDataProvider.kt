package com.hirezy.composeuI.home.data

internal object MenuDataProvider {
    val menuGroups = MenuGroups.filter { it.title != "地图组件" }
}
