package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.ProductRepository
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Mappers.toBasketProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel?>(null)
    val product: LiveData<ProductUiModel?> = _product

    fun setProduct(product: ProductUiModel?) {
        Timber.d("setProduct: $product")
        _product.value = product
    }

    suspend fun addToCart(product: ProductUiModel) {
        productRepository.getBasketProductFromId(product.id).let {
            Timber.d("getBasketProductFromId result: $it")
            if (it != null) {
                productRepository.updateBasketProduct(it.copy(basketQuantity = it.basketQuantity + 1))
            } else {
                productRepository.insertBasketProduct(product.toBasketProductEntity(1))
            }
        }
    }
}