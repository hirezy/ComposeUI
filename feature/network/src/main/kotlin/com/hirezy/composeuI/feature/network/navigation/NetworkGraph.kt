package com.hirezy.composeuI.feature.network.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.network.download.FileDownloadScreen
import com.hirezy.composeuI.feature.network.request.HttpRequestScreen
import com.hirezy.composeuI.feature.network.upload.FileUploadScreen
import com.hirezy.composeuI.feature.network.websocket.WebSocketScreen

fun NavGraphBuilder.addNetworkGraph() {
    composable("http_request") {
        HttpRequestScreen()
    }
    composable("file_upload") {
        FileUploadScreen()
    }
    composable("file_download") {
        FileDownloadScreen()
    }
    composable("web_socket") {
        WebSocketScreen()
    }
}