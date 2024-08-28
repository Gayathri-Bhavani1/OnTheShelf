package com.paltech.ontheshelf.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paltech.ontheshelf.di.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var loginStatus = mutableStateOf<LoginStatus>(LoginStatus.Idle)
        private set

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onLogin() {
        if (validateEmail(email.value) && validatePassword(password.value)) {
            loginStatus.value = LoginStatus.Loading

            viewModelScope.launch {
                val result = async{ loginRepository.login(email.value, password.value)}.await()
                loginStatus.value = if (result.isSuccess) {

                    LoginStatus.Success
                } else {
                    LoginStatus.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        } else {
            loginStatus.value = LoginStatus.Error("Invalid email or password")
        }
    }

    private fun validateEmail(email: String): Boolean {
        // Email validation logic
        return true
    }

    private fun validatePassword(password: String): Boolean {
        // Password validation logic
        return true
    }

    sealed class LoginStatus {
        data object Idle : LoginStatus()
        data object Loading : LoginStatus()
        data object Success : LoginStatus()
        data class Error(val message: String) : LoginStatus()
    }
}
