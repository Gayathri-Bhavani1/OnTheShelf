package com.paltech.ontheshelf.presentation.basket

data class BasketItem(
    val id: Int,
    val name: String,
    val imageResId: Int,
    var quantity: Int,
    val price: Double
)
