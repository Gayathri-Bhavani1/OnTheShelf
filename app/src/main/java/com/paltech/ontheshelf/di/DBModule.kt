package com.paltech.ontheshelf.di
import android.content.Context
import androidx.room.Room
import com.paltech.ontheshelf.data.localdb.AppDatabase
import com.paltech.ontheshelf.data.localdb.BasketDao
import com.paltech.ontheshelf.data.localdb.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideBasketDao(appDatabase: AppDatabase): BasketDao {
        return appDatabase.basketDao()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }
}
