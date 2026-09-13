package com.hirezy.composeuI.feature.samples.filebrowser.filelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hirezy.composeuI.core.data.model.Result
import com.hirezy.composeuI.core.ui.components.loading.LoadMoreType
import com.hirezy.composeuI.core.ui.components.loading.WeLoadMore
import com.hirezy.composeuI.core.ui.components.refreshview.WeRefreshView
import com.hirezy.composeuI.core.utils.openFile
import com.hirezy.composeuI.feature.samples.filebrowser.data.model.FileItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun FileListScreen(
    path: String,
    fileViewModel: FileListViewModel = viewModel(),
    onNavigateTo: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(path) {
        if (fileViewModel.fileListResult == Result.Loading) {
            fileViewModel.refresh(path)
        }
    }

    WeRefreshView(onRefresh = {
        delay(1000)
        fileViewModel.refresh(path)
    }) {
        FileList(
            fileListResult = fileViewModel.fileListResult,
            onNavigateTo = {
                onNavigateTo(it)
            }
        ) {
            coroutineScope.launch {
                fileViewModel.refresh(path)
            }
        }
    }
}

@Composable
private fun FileList(
    fileListResult: Result<List<FileItem>>,
    onNavigateTo: (String) -> Unit,
    onDeleted: () -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        when (fileListResult) {
            is Result.Loading -> {
                item {
                    WeLoadMore()
                }
            }

            is Result.Success -> {
                val fileList = fileListResult.data
                if (fileList.isEmpty()) {
                    item {
                        WeLoadMore(type = LoadMoreType.ALL_LOADED)
                    }
                }
                if (fileList.isNotEmpty()) {
                    items(fileList, key = { it.path }) { item ->
                        FileListItem(
                            item,
                            onFolderClick = {
                                onNavigateTo(item.path)
                            },
                            onFileClick = {
                                context.openFile(File(item.path), item.mimeType)
                            },
                            onDeleted
                        )
                    }
                }
            }

            else -> {}
        }
    }
}