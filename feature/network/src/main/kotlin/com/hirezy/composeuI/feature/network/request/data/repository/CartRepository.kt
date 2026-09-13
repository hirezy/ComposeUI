package com.hirezy.composeuI.feature.network.request.data.repository

import com.hirezy.composeuI.feature.network.request.data.model.RecommendItem
import com.hirezy.composeuI.feature.network.request.data.model.Result


interface CartRepository {
    suspend fun fetchRecommendProducts(): Result<List<RecommendItem>>?
}