package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.ProductRepository
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Mappers.toBasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Mappers.toFavoriteProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _isFavoriteListEmpty = MutableLiveData<Boolean>()
    val isFavoriteListEmpty: LiveData<Boolean> = _isFavoriteListEmpty

    private val _products = MutableLiveData<List<ProductUiModel>>()
    val products: LiveData<List<ProductUiModel>> = _products

    suspend fun refreshList() {
        val favoriteList = productRepository.getAllFavoriteProducts()

        if (favoriteList.isEmpty()) {
            _isFavoriteListEmpty.postValue(true)
        } else {
            _isFavoriteListEmpty.postValue(false)

            _products.postValue(favoriteList.map {
                ProductUiModel(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    price = it.price,
                    description = it.description,
                    isFavorite = true,
                    dateCreated = ""
                )
            })
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

    suspend fun removeFromFavorite(it: ProductUiModel) {
        productRepository.deleteFavoriteProduct(it.toFavoriteProductEntity())
        refreshList()
    }
}