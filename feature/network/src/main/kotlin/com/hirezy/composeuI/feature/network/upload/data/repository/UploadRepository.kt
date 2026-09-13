package com.hirezy.composeuI.feature.network.upload.data.repository

import com.hirezy.composeuI.feature.network.upload.data.model.UploadResult
import okhttp3.MultipartBody

interface UploadRepository {
    suspend fun uploadFile(file: MultipartBody.Part): UploadResult?
}