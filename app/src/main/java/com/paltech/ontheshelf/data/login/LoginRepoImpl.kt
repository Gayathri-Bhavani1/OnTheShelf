package com.paltech.ontheshelf.data.login


import com.paltech.ontheshelf.di.LoginRepository
import com.paltech.ontheshelf.di.UserRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        // Simulate network call
        return if (email !=null && password !=null) {
            // Set the user as logged in
            userRepository.setUserLoggedIn(true)
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }
}
