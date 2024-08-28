package com.paltech.ontheshelf.presentation.basket.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.paltech.ontheshelf.data.localdb.BasketItemEntity
import com.paltech.ontheshelf.di.BasketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(private val basketRepository: BasketRepository) : ViewModel() {

    val basketItems = basketRepository.basketItems.asLiveData()

    // LiveData properties for subtotal, delivery fee, and total
    val subtotal = mutableStateOf(0.0)
    val deliveryFee = 5.0 // Example fixed delivery fee
    val total = mutableStateOf(0.0)

    init {
        // Observe changes to basketItems and update totals accordingly
        basketItems.observeForever {
            updateTotals(it)
        }
    }

    fun addItem(name: String, imageResId: Int, price: Double,quantity:Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newItem = BasketItemEntity(name = name, imageResId = imageResId, quantity = quantity, price = price,)
                basketRepository.addBasketItem(newItem)
            }
        }
    }

    fun incrementQuantity(item: BasketItemEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val updatedItem = item.copy(quantity = item.quantity + 1)
                basketRepository.updateBasketItem(updatedItem)
            }
        }
    }

    fun decrementQuantity(item: BasketItemEntity) {
        if (item.quantity > 1) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val updatedItem = item.copy(quantity = item.quantity - 1)
                    basketRepository.updateBasketItem(updatedItem)
                }
            }
        }
    }

    fun removeItem(item: BasketItemEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                basketRepository.removeBasketItem(item)
            }
        }
    }

    private fun updateTotals(items: List<BasketItemEntity>) {
        val newSubtotal = items.sumOf { it.price * it.quantity }
        subtotal.value = newSubtotal
        total.value = newSubtotal + deliveryFee
    }
}
