package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.ProductRepository
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Mappers.toBasketProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _isRemoteListEmpty = MutableLiveData<Boolean>()
    val isRemoteListEmpty: LiveData<Boolean> = _isRemoteListEmpty

    private val _products = MutableLiveData<List<ProductUiModel>>()
    val products: LiveData<List<ProductUiModel>> = _products

    init {
        viewModelScope.launch {
            try {
                productRepository.fetchProducts().body().let {
                    Timber.d("Received body: $it")
                    if (it.isNullOrEmpty()) {
                        _isRemoteListEmpty.postValue(true)
                    } else {
                        _isRemoteListEmpty.postValue(false)
                        _products.value = it.map { productResponseModel ->
                            ProductUiModel(
                                id = productResponseModel.id.toInt(),
                                name = productResponseModel.name,
                                imageUrl = productResponseModel.image,
                                price = productResponseModel.price,
                                description = productResponseModel.description
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching products")
                _isRemoteListEmpty.postValue(true)
            }
        }
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