package com.paltech.ontheshelf.presentation.Vegetables

import com.paltech.ontheshelf.di.ProductRepository
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.paltech.ontheshelf.presentation.home.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = mutableStateListOf<Product>()
    val products: List<Product> get() = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.addAll(productRepository.getProducts())
    }

    fun toggleFavorite(product: Product) {
        product.isFavorite.value = !product.isFavorite.value
    }
// or inject using Hilt


}
