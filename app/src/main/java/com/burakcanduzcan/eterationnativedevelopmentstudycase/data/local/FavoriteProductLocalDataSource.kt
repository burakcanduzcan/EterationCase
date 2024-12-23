package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.dao.FavoriteProductDao
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.FavoriteProductEntity
import javax.inject.Inject

class FavoriteProductLocalDataSource @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) {
    fun getFavoriteProductsLiveData() = favoriteProductDao.getFavoriteProductsLiveData()

    suspend fun getFavoriteProductFromId(id: Int) = favoriteProductDao.getFavoriteProductFromId(id)

    suspend fun insert(favoriteProductEntity: FavoriteProductEntity) =
        favoriteProductDao.insert(favoriteProductEntity)

    suspend fun delete(favoriteProductEntity: FavoriteProductEntity) =
        favoriteProductDao.delete(favoriteProductEntity)
}