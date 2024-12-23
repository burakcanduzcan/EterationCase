package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.remote

import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val mockApiService: MockApiService) {
    suspend fun fetchProducts(): Response<List<ProductResponseModel>> {
        return mockApiService.fetchProducts()
    }
}