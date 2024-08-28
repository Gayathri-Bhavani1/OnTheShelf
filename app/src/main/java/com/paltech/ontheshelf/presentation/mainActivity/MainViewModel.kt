package com.paltech.ontheshelf.presentation.mainActivity


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paltech.ontheshelf.di.UserRepository
import com.paltech.ontheshelf.domain.usecases.app_entry.AppEntryUseCases
import com.paltech.ontheshelf.presentation.navigation.Route

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            if (shouldStartFromHomeScreen) {
                // Check if the user is logged in
                if (userRepository.isUserLoggedIn()) {
                    // If logged in, go to the main navigation
                    _startDestination.value = Route.MainNavigation.route
                } else {
                    // If not logged in, go to the login screen
                    _startDestination.value = Route.LoginNavigation.route
                }
            } else {
                // Show the onboarding screen
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(200) // Without this delay, the onboarding screen might flash briefly
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}
