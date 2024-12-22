package com.burakcanduzcan.eterationnativedevelopmentstudycase.util

import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.BasketProductEntity
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
}