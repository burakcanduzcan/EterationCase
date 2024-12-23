package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _basketCount = MutableLiveData<Int>()
    val basketCount: LiveData<Int> = _basketCount

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _basketCount.postValue(productRepository.getBasketProductSize())
        }
    }

    fun updateBasketCount(amount: Int) {
        _basketCount.value = (basketCount.value ?: 0) + amount
    }
}