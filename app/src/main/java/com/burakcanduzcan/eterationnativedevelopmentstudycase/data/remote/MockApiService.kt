package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface MockApiService {
    @GET("/products")
    suspend fun fetchProducts(): Response<List<ProductResponseModel>>
}