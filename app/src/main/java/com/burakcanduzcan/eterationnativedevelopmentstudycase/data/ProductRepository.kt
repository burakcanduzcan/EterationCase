package com.burakcanduzcan.eterationnativedevelopmentstudycase.data

import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.BasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.BasketProductLocalDataSource
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val basketProductLocalDataSource: BasketProductLocalDataSource
) {
    suspend fun getProducts() = productRemoteDataSource.getProducts()

    suspend fun getAllBasketProducts() = basketProductLocalDataSource.getAll()

    suspend fun insertBasketProduct(basketProductEntity: BasketProductEntity) =
        basketProductLocalDataSource.insert(basketProductEntity)

    suspend fun updateBasketProduct(basketProductEntity: BasketProductEntity) =
        basketProductLocalDataSource.update(basketProductEntity)

    suspend fun deleteBasketProduct(basketProductEntity: BasketProductEntity) =
        basketProductLocalDataSource.delete(basketProductEntity)
}