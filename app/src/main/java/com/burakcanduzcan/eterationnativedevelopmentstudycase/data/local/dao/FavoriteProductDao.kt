package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.FavoriteProductEntity

@Dao
interface FavoriteProductDao {
    @Query("SELECT * FROM FavoriteProductEntity")
    fun getFavoriteProductsLiveData(): LiveData<List<FavoriteProductEntity>>

    @Query("SELECT * FROM FavoriteProductEntity WHERE id = :id")
    suspend fun getFavoriteProductFromId(id: Int): FavoriteProductEntity?

    @Insert
    suspend fun insert(favoriteProductEntity: FavoriteProductEntity)

    @Delete
    suspend fun delete(favoriteProductEntity: FavoriteProductEntity)
}