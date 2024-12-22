package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import timber.log.Timber

class ProductDetailViewModel : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel?>(null)
    val product: LiveData<ProductUiModel?> = _product

    fun setProduct(product: ProductUiModel?) {
        Timber.d("setProduct: $product")
        _product.value = product
    }

    fun addToCart(product: ProductUiModel) {
        // TODO: first check if it is in cart. if it is in cart, increase quantity. if not, add to cart
    }
}