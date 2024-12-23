package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.ProductRepository
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.BasketProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    val basketProducts: LiveData<List<BasketProductEntity>> =
        productRepository.getBasketProductsLiveData()

    private val _totalValue = MutableLiveData(0.0)
    val totalValue: LiveData<Double> = _totalValue

    private val _isBasketEmpty = MutableLiveData<Boolean>()
    val isBasketEmpty: LiveData<Boolean> = _isBasketEmpty

    suspend fun addToCart(basketProduct: BasketProductEntity) {
        productRepository.updateBasketProduct(basketProduct.copy(basketQuantity = basketProduct.basketQuantity + 1))
    }

    suspend fun removeFromCart(basketProduct: BasketProductEntity) {
        if (basketProduct.basketQuantity <= 1) {
            productRepository.deleteBasketProduct(basketProduct)
        } else {
            productRepository.updateBasketProduct(basketProduct.copy(basketQuantity = basketProduct.basketQuantity - 1))
        }
    }

    fun calculateTotalValue() {
        _totalValue.value = basketProducts.value!!.sumOf { it.price.toDouble() * it.basketQuantity }
    }

    fun setBasketEmpty(isEmpty: Boolean) {
        _isBasketEmpty.value = isEmpty
    }
}