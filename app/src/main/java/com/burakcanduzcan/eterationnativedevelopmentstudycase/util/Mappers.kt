package com.burakcanduzcan.eterationnativedevelopmentstudycase.util

import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.BasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.FavoriteProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel

object Mappers {
    fun ProductUiModel.toBasketProductEntity(basketQuantity: Int = 0): BasketProductEntity {
        return BasketProductEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            basketQuantity = basketQuantity
        )
    }

    fun ProductUiModel.toFavoriteProductEntity(): FavoriteProductEntity {
        return FavoriteProductEntity(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl,
            price = this.price,
            description = this.description
        )
    }
}