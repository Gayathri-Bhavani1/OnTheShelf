package com.paltech.ontheshelf.di

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
