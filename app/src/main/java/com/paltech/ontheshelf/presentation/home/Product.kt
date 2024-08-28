package com.paltech.ontheshelf.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Product(
    val id :Int =0,
    val name: String,
    val price: Double,
    val discount: Int,
    val imageResId: Int,
    val description: String = "Hand picked,extra large juicy oranges imported from Brazil. 1 piece weighs about 200 grams",
    var quantity:Int,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)


data class Category(
    val name: String,
    val iconResId: Int,
)
