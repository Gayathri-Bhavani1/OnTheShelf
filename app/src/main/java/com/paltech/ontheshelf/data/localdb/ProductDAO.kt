package com.paltech.ontheshelf.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.paltech.ontheshelf.presentation.home.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(product: ProductEntity)

    @Delete
   fun deleteFavorite(product: ProductEntity)

    @Query("SELECT * FROM favorites")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM favorites WHERE id IN (:ids)")
    fun getProductsByIds(ids: Set<Int>): List<ProductEntity>

    @Query("SELECT * FROM favorites WHERE id = :productId LIMIT 1")
    fun getProductById(productId: String): ProductEntity?
}
