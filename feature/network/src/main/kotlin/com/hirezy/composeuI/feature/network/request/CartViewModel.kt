package com.hirezy.composeuI.feature.network.request

import androidx.lifecycle.ViewModel
import com.hirezy.composeuI.feature.network.request.data.model.RecommendItem
import com.hirezy.composeuI.feature.network.request.data.repository.CartRepositoryImpl
import com.hirezy.composeuI.feature.network.request.data.model.Result

class CartViewModel : ViewModel() {
    private val cartRepository by lazy {
        CartRepositoryImpl()
    }

    suspend fun fetchRecommendProducts(): Result<List<RecommendItem>>? {
        return cartRepository.fetchRecommendProducts()
    }
}