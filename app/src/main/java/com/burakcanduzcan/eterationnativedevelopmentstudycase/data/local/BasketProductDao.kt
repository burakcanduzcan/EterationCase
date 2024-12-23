package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BasketProductDao {
    @Query("SELECT * FROM BasketProductEntity")
    fun getBasketProductsLiveData(): LiveData<List<BasketProductEntity>>

    @Query("SELECT * FROM BasketProductEntity WHERE id = :id")
    suspend fun getBasketProductFromId(id: Int): BasketProductEntity?

    @Query("SELECT SUM(basketQuantity) FROM BasketProductEntity")
    suspend fun getBasketProductSize(): Int

    @Insert
    suspend fun insert(basketProductEntity: BasketProductEntity)

    @Update
    suspend fun update(basketProductEntity: BasketProductEntity)

    @Delete
    suspend fun delete(basketProductEntity: BasketProductEntity)
}