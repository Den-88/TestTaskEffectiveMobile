package com.den.shak.data.network

import com.den.shak.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("uc")
    suspend fun getApiResponse(@Query("id") fileId: String = "1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r", @Query("export") export: String = "download"): ApiResponse
}
