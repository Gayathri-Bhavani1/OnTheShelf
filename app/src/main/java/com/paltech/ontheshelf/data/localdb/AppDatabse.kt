package com.paltech.ontheshelf.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ProductEntity::class, BasketItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

   abstract fun basketDao() : BasketDao
}
