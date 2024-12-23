package com.burakcanduzcan.eterationnativedevelopmentstudycase.data

import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.BasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.BasketProductLocalDataSource
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.FavoriteProductLocalDataSource
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.FavoriteProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val basketProductLocalDataSource: BasketProductLocalDataSource,
    private val favoriteProductLocalDataSource: FavoriteProductLocalDataSource
) {
    suspend fun fetchProducts() = productRemoteDataSource.fetchProducts()

    //region basketProductLocalDataSource
    fun getBasketProductsLiveData() = basketProductLocalDataSource.getBasketProductsLiveData()

    suspend fun getBasketProductFromId(id: Int) =
        basketProductLocalDataSource.getBasketProductFromId(id)

    suspend fun getBasketProductSize() = basketProductLocalDataSource.getBasketProductSize()

    suspend fun insertBasketProduct(basketProductEntity: BasketProductEntity) =
        basketProductLocalDataSource.insert(basketProductEntity)

    suspend fun updateBasketProduct(basketProductEntity: BasketProductEntity) =
        basketProductLocalDataSource.update(basketProductEntity)

    suspend fun deleteBasketProduct(basketProductEntity: BasketProductEntity) =
        basketProductLocalDataSource.delete(basketProductEntity)
    //endregion

    //region favoriteProductLocalDataSource
    suspend fun getAllFavoriteProducts() = favoriteProductLocalDataSource.getAllFavoriteProducts()

    suspend fun getFavoriteProductFromId(id: Int) =
        favoriteProductLocalDataSource.getFavoriteProductFromId(id)

    suspend fun insertFavoriteProduct(favoriteProductEntity: FavoriteProductEntity) =
        favoriteProductLocalDataSource.insert(favoriteProductEntity)

    suspend fun deleteFavoriteProduct(favoriteProductEntity: FavoriteProductEntity) =
        favoriteProductLocalDataSource.delete(favoriteProductEntity)
    //endregion
}