package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.ProductRepository
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Mappers.toBasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Mappers.toFavoriteProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _filteredProducts = MutableLiveData<List<ProductUiModel>>()
    val filteredProducts: LiveData<List<ProductUiModel>> = _filteredProducts

    init {
        viewModelScope.launch {
            val favoriteList = productRepository.getAllFavoriteProducts()

            try {
                productRepository.fetchProducts().body().let {
                    Timber.d("Received body: $it")
                    if (it.isNullOrEmpty()) {
                        _isRemoteListEmpty.postValue(true)
                    } else {
                        _isRemoteListEmpty.postValue(false)

                        withContext(Dispatchers.Main) {
                            _products.value = it.map { productResponseModel ->
                                ProductUiModel(
                                    id = productResponseModel.id.toInt(),
                                    name = productResponseModel.name,
                                    imageUrl = productResponseModel.image,
                                    price = productResponseModel.price,
                                    description = productResponseModel.description,
                                    isFavorite = favoriteList.any { favoriteProductEntity ->
                                        favoriteProductEntity.id == productResponseModel.id.toInt()
                                    }
                                )
                            }
                            _filteredProducts.value = _products.value
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching products")
                _isRemoteListEmpty.postValue(true)
            }
        }
    }

    private suspend fun refreshList() {
        val favoriteList = productRepository.getAllFavoriteProducts()

        _products.postValue(
            _products.value?.map { item ->
                ProductUiModel(
                    id = item.id,
                    name = item.name,
                    imageUrl = item.imageUrl,
                    price = item.price,
                    description = item.description,
                    isFavorite = favoriteList.any { it.id == item.id }
                )
            }
        )
        _filteredProducts.postValue(_products.value)
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

    fun searchProducts(query: String) {
        val filteredList =
            _products.value?.filter { it.name.contains(query, ignoreCase = true) } ?: emptyList()
        _filteredProducts.value = filteredList
    }

    suspend fun isItemInFavorite(id: Int): Boolean {
        return productRepository.getFavoriteProductFromId(id) != null
    }

    suspend fun addToFavorite(product: ProductUiModel) {
        productRepository.insertFavoriteProduct(product.toFavoriteProductEntity())
        refreshList()
    }

    suspend fun removeFromFavorite(it: ProductUiModel) {
        productRepository.deleteFavoriteProduct(it.toFavoriteProductEntity())
        refreshList()
    }
}