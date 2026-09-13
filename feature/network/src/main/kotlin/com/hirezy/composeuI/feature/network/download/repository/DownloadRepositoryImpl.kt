package com.hirezy.composeuI.feature.network.download.repository

import com.hirezy.composeuI.feature.network.download.retrofit.DownloadService
import com.hirezy.composeuI.feature.network.download.retrofit.RetrofitManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.IOException

class DownloadRepositoryImpl : DownloadRepository {
    private val downloadService by lazy {
        RetrofitManger.retrofit.create(DownloadService::class.java)
    }

    override suspend fun downloadFile(filename: String): ResponseBody? {
        return withContext(Dispatchers.IO) {
            try {
                downloadService.downloadFile(filename)
            } catch (e: IOException) {
                null
            }
        }
    }
}