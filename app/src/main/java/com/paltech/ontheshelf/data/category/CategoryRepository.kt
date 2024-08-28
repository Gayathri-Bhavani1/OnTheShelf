package com.paltech.ontheshelf.data.category


import com.paltech.ontheshelf.presentation.home.Category



interface CategoriesRepository {
    fun getCategories(): List<Category>
    fun getSubCategoriesForCategory(category: String): List<Category>
}
