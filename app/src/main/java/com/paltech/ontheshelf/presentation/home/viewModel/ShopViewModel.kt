package com.paltech.ontheshelf.presentation.home.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.data.localdb.ProductEntity
import com.paltech.ontheshelf.di.UserRepository
import com.paltech.ontheshelf.domain.ProductRepository
import com.paltech.ontheshelf.presentation.home.Category
import com.paltech.ontheshelf.presentation.home.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ShopViewModel @Inject constructor(private val repository: ProductRepository,

    private val userRepository: UserRepository,

) : ViewModel() {


    private var items : MutableList<Any> = mutableListOf()

    private val _productsOnSale = MutableStateFlow<List<Product>>(emptyList())
    val productsOnSale: StateFlow<List<Product>> = _productsOnSale

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _orderAgainProducts = MutableStateFlow<List<Product>>(emptyList())
    val orderAgainProducts: StateFlow<List<Product>> = _orderAgainProducts

    private val _favorites = MutableStateFlow<Set<Int>>(emptySet()) // Track favorite IDs
    val favorites: StateFlow<Set<Int>> = _favorites
    // Use a mutable list to manage and update items



    init {
        loadInitialData()

        viewModelScope.launch {
            repository.favoriteProducts.collect { products ->
                _favorites.value = products.map { it.id }.toSet()
                updateProductFavoriteState(products)
            }
        }
    }

    private fun loadInitialData() {
        _productsOnSale.value = listOf(
            Product(id = 1, name = "Oranges", price = 2.0, discount = 30, imageResId = R.drawable.orange, quantity = 1,


                ),
            Product(id = 2, name = "Watermelon", price = 5.0, discount = 10, imageResId = R.drawable.watermelon, quantity = 1,


                )
        )

        items = mutableListOf( Product(id = 1, name = "Oranges", price = 2.0, discount = 30, imageResId = R.drawable.orange, quantity = 1),
    Product(id = 2, name = "Watermelon", price = 5.0, discount = -10, imageResId = R.drawable.watermelon, quantity = 1),
    Product(id = 3, name = "Bacon", price = 1.0, discount = 0, imageResId = R.drawable.bacon, quantity = 1),
    Product(id = 4, name = "Bananas", price = 2.0, discount =0, imageResId = R.drawable.banana, quantity = 1)

)

        _categories.value = listOf(
            Category("New in", R.drawable.store),
            Category("SuperSale", R.drawable.sale),
            Category("Bakery", R.drawable.bakery),
            Category("Fruits", R.drawable.apple),
            Category("Meat", R.drawable.leg),
            Category("Fish", R.drawable.fish),
            Category("Vegetables", R.drawable.plant),
            Category("Dairy", R.drawable.box)
        )

        _orderAgainProducts.value = listOf(
            Product(id = 3, name = "Bacon", price = 1.0, discount = 0, imageResId = R.drawable.bacon, quantity = 1),
            Product(id = 4, name = "Bananas", price = 2.0, discount = 0, imageResId = R.drawable.banana, quantity = 1)
        )
    }

    private fun updateProductFavoriteState(favoriteProducts: List<ProductEntity>) {
        val favoriteIds = favoriteProducts.map { it.id }.toSet()

        _productsOnSale.value = _productsOnSale.value.map { product ->
            product.copy(isFavorite = mutableStateOf(product.id in favoriteIds))
        }

        _orderAgainProducts.value = _orderAgainProducts.value.map { product ->
            product.copy(isFavorite = mutableStateOf(product.id in favoriteIds))
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productEntity = ProductEntity(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    quantity = product.quantity,
                    discount = product.discount,
                    imageResId = product.imageResId,
                    isFavorite = !product.isFavorite.value,
                    description = product.description
                )

                if (product.isFavorite.value) {
                    repository.removeFavorite(productEntity)
                } else {
                    repository.addFavorite(productEntity)
                }

                _favorites.value = if (product.isFavorite.value) {
                    _favorites.value - product.id
                } else {
                    _favorites.value + product.id
                }

                product.isFavorite.value = !product.isFavorite.value
            }
        }
    }

    fun getProductsByIds(ids: Set<Int>, onResult: (List<ProductEntity>) -> Unit) {
        viewModelScope.launch {
            val products = repository.getProductsByIds(ids)
            onResult(products)
        }
    }
    fun getProductById(id: Int): Product? {
        return items.find {
            it is Product && it.id == id
        } as? Product
    }
    fun updateProductQuantity(id: Int, newQuantity: Int) {
        viewModelScope.launch {
            _productsOnSale.value = _productsOnSale.value.map { product ->
                if (product.id == id) {
                    product.copy(quantity = newQuantity)
                } else {
                    product
                }
            }

        }
    }


    fun logout(){
        userRepository.logout();
    }
}

