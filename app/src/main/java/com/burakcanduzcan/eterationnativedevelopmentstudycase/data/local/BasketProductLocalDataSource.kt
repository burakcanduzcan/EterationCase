package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import javax.inject.Inject

class BasketProductLocalDataSource @Inject constructor(private val basketProductDao: BasketProductDao) {
    suspend fun getAll() = basketProductDao.getAll()

    suspend fun insert(basketProductEntity: BasketProductEntity) =
        basketProductDao.insert(basketProductEntity)

    suspend fun update(basketProductEntity: BasketProductEntity) =
        basketProductDao.update(basketProductEntity)

    suspend fun delete(basketProductEntity: BasketProductEntity) =
        basketProductDao.delete(basketProductEntity)
}