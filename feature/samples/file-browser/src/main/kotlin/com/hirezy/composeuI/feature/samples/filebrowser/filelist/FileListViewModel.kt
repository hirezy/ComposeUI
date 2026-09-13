package com.hirezy.composeuI.feature.samples.filebrowser.filelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hirezy.composeuI.core.data.model.Result
import com.hirezy.composeuI.feature.samples.filebrowser.data.model.FileItem
import com.hirezy.composeuI.feature.samples.filebrowser.data.repository.FileRepositoryImpl

class FileListViewModel : ViewModel() {
    private val fileRepository = FileRepositoryImpl()

    var fileListResult by mutableStateOf<Result<List<FileItem>>>(Result.Loading)
        private set

    suspend fun refresh(filePath: String) {
        fileListResult = Result.Success(fileRepository.getFileList(filePath))
    }
}