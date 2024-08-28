package com.paltech.ontheshelf.di

import CategoriesRepositoryImpl
import android.app.Application
import android.content.Context
import com.paltech.ontheshelf.data.category.CategoriesRepository
import com.paltech.ontheshelf.data.login.LoginRepositoryImpl
import com.paltech.ontheshelf.data.manager.LocalUserMangerImpl
import com.paltech.ontheshelf.domain.manager.LocalUserManger
import com.paltech.ontheshelf.domain.usecases.app_entry.AppEntryUseCases
import com.paltech.ontheshelf.domain.usecases.app_entry.ReadAppEntry
import com.paltech.ontheshelf.domain.usecases.app_entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideUserRepository(
        context: Context
    ): UserRepository = UserRepository(context)

    @Provides
    @Singleton
    fun provideLocalUserManger(
        context: Context
    ): LocalUserManger = LocalUserMangerImpl(context)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

    @Provides
    @Singleton
    fun provideCategoriesRepository(): CategoriesRepository {
        return CategoriesRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository {
        return ProductRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        userRepository: UserRepository
    ): LoginRepository {
        return LoginRepositoryImpl(userRepository)
    }
}
