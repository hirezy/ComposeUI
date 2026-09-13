package com.hirezy.composeuI.feature.samples.filebrowser.data.repository

import com.hirezy.composeuI.feature.samples.filebrowser.data.model.FileItem

interface FileRepository {
    suspend fun getFileList(filepath: String): List<FileItem>
}