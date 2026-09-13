package com.hirezy.composeuI.feature.network.request.data.repository

import com.hirezy.composeuI.feature.network.request.data.model.RecommendItem
import com.hirezy.composeuI.feature.network.request.retrofit.CartService
import com.hirezy.composeuI.feature.network.request.retrofit.RetrofitManger
import com.hirezy.composeuI.feature.network.request.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CartRepositoryImpl : CartRepository {
    private val cartService by lazy {
        RetrofitManger.retrofit.create(CartService::class.java)
    }

    override suspend fun fetchRecommendProducts(): Result<List<RecommendItem>>? {
        return withContext(Dispatchers.IO) {
            try {
                cartService.fetchRecommendProducts()
            } catch (e: IOException) {
                null
            }
        }
    }
}