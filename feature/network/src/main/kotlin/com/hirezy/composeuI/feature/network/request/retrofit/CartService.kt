package com.hirezy.composeuI.feature.network.request.retrofit

import com.hirezy.composeuI.feature.network.request.data.model.RecommendItem
import com.hirezy.composeuI.feature.network.request.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Header

interface CartService {
    @GET("rec/cartempty")
    suspend fun fetchRecommendProducts(
        @Header("Referer") referer: String = "https://www.mi.com"
    ): Result<List<RecommendItem>>
}