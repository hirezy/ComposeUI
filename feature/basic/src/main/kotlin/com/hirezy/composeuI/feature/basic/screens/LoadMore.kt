package com.hirezy.composeuI.feature.basic.screens

import androidx.compose.runtime.Composable
import com.hirezy.composeuI.core.ui.components.loading.LoadMoreType
import com.hirezy.composeuI.core.ui.components.loading.WeLoadMore
import com.hirezy.composeuI.core.ui.components.screen.WeScreen

@Composable
fun LoadMoreScreen() {
    WeScreen(title = "LoadMore", description = "加载更多") {
        WeLoadMore(type = LoadMoreType.LOADING)
        WeLoadMore(type = LoadMoreType.EMPTY_DATA)
        WeLoadMore(type = LoadMoreType.ALL_LOADED)
    }
}