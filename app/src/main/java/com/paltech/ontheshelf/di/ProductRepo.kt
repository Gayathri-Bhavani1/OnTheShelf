package com.paltech.ontheshelf.di


import com.paltech.ontheshelf.presentation.home.Product
import com.paltech.ontheshelf_prod.R
import javax.inject.Inject


interface ProductRepository {
    fun getProducts(): List<Product>
}

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override fun getProducts(): List<Product> {
        return listOf(
            Product( 1,"Cucumber", 0.99, 30, R.drawable.cucumber, quantity = 1),
            Product(2,"Broccoli", 2.0, 10, R.drawable.brocali, quantity = 1),
            Product(3,"Peas", 0.87, 100, R.drawable.peas, quantity = 1),
            Product(4,"Hokkaido", 3.49, 100, R.drawable.hokkaido, quantity = 1)
        )
    }
}
