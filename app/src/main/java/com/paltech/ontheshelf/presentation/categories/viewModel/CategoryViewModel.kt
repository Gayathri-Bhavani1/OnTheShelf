package com.paltech.ontheshelf.presentation.categories.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paltech.ontheshelf.data.category.CategoriesRepository
import com.paltech.ontheshelf.presentation.home.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: CategoriesRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _subCategories = MutableLiveData<Map<String, List<Category>>>()
    val subCategories: LiveData<Map<String, List<Category>>> = _subCategories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categories.value = repository.getCategories()
        _subCategories.value = mapOf(
            "Food" to repository.getSubCategoriesForCategory("Food"),
            "Beverages" to repository.getSubCategoriesForCategory("Beverages"),
            "Other" to repository.getSubCategoriesForCategory("Other")
        )
    }
}
