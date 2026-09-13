package com.hirezy.composeuI.feature.network.request.data.model

data class Result<out T>(
    val code: Int,
    val msg: String? = null,
    val data: T? = null
)