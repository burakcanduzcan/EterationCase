package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.dao.BasketProductDao
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.BasketProductEntity
import javax.inject.Inject

class BasketProductLocalDataSource @Inject constructor(
    private val basketProductDao: BasketProductDao
) {
    fun getBasketProductsLiveData() = basketProductDao.getBasketProductsLiveData()

    suspend fun getBasketProductFromId(id: Int) = basketProductDao.getBasketProductFromId(id)

    suspend fun getBasketProductSize() = basketProductDao.getBasketProductSize()

    suspend fun insert(basketProductEntity: BasketProductEntity) =
        basketProductDao.insert(basketProductEntity)

    suspend fun update(basketProductEntity: BasketProductEntity) =
        basketProductDao.update(basketProductEntity)

    suspend fun delete(basketProductEntity: BasketProductEntity) =
        basketProductDao.delete(basketProductEntity)
}