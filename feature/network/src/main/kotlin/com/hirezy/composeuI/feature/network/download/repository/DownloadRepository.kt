package com.hirezy.composeuI.feature.network.download.repository

import okhttp3.ResponseBody

interface DownloadRepository {
    suspend fun downloadFile(filename: String): ResponseBody?
}