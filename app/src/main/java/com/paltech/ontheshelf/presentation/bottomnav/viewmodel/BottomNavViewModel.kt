package com.paltech.ontheshelf.presentation.bottomnav.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BottomNavViewModel @Inject constructor() : ViewModel() {
    var selectedItemIndex by mutableStateOf(0)
        private set

    private val routeToIndexMap = bottomNavItems.mapIndexed { index, item -> item.route to index }.toMap()

    fun selectItem(index: Int) {
        selectedItemIndex = index
    }

    fun updateIndexBasedOnRoute(route: String) {
        routeToIndexMap[route]?.let { selectItem(it) }
    }
}
