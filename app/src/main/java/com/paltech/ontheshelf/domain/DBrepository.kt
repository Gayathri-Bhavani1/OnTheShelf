package com.paltech.ontheshelf.domain

import com.paltech.ontheshelf.data.localdb.ProductDao
import com.paltech.ontheshelf.data.localdb.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {

    val favoriteProducts: Flow<List<ProductEntity>> = productDao.getFavoriteProducts()

    fun addFavorite(product: ProductEntity) {
        productDao.insertFavorite(product)

    }

    fun removeFavorite(product: ProductEntity) {
        productDao.deleteFavorite(product)
    }

    suspend fun getProductsByIds(ids: Set<Int>): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            productDao.getProductsByIds(ids)
        }
    }


}
