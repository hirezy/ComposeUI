package com.hirezy.composeuI.feature.basic.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hirezy.composeuI.core.ui.components.cardlist.WeCardListItem
import com.hirezy.composeuI.core.ui.components.cardlist.cardList
import com.hirezy.composeuI.core.ui.components.loading.WeLoadMore
import com.hirezy.composeuI.core.ui.components.refreshview.WeRefreshView
import com.hirezy.composeuI.core.ui.components.refreshview.rememberLoadMoreState
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import kotlinx.coroutines.delay

@Composable
fun RefreshViewScreen() {
    WeScreen(title = "RefreshView", description = "可刷新视图", scrollEnabled = false) {
        val listState = rememberLazyListState()
        val listItems = remember {
            mutableStateListOf<String>().apply {
                addAll(List(30) { "${it + 1}" })
            }
        }
        val loadMoreState = rememberLoadMoreState {
            delay(2000)
            listItems.addAll(List(30) { index -> "${listItems.size + index + 1}" })
        }

        WeRefreshView(
            modifier = Modifier.nestedScroll(loadMoreState.nestedScrollConnection),
            onRefresh = {
                delay(2000)
                listItems.clear()
                listItems.addAll(List(30) { "${it + 1}" })
            }
        ) {
            LazyColumn(state = listState, modifier = Modifier.cardList()) {
                items(listItems, key = { it }) {
                    WeCardListItem(label = "第${it}行")
                }
                item {
                    if (loadMoreState.isLoadingMore) {
                        WeLoadMore(listState = listState)
                    }
                }
            }
        }
    }
}