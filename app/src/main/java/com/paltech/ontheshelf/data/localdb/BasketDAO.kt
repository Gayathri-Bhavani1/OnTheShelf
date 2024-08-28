package com.paltech.ontheshelf.data.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface BasketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertBasketItem(basketItem: BasketItemEntity)

    @Query("SELECT * FROM basket") // Fix the table name here
    fun getAllBasketItems(): Flow<List<BasketItemEntity>>

    @Update
     fun updateBasketItem(basketItem: BasketItemEntity)

    @Delete
     fun deleteBasketItem(basketItem: BasketItemEntity)
}
