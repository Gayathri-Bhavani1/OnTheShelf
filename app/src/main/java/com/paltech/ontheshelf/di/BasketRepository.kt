package com.paltech.ontheshelf.di


import com.paltech.ontheshelf.data.localdb.BasketDao
import com.paltech.ontheshelf.data.localdb.BasketItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketRepository @Inject constructor(private val basketDao: BasketDao) {
    val basketItems: Flow<List<BasketItemEntity>> = basketDao.getAllBasketItems()

    suspend fun addBasketItem(item: BasketItemEntity) {
        basketDao.insertBasketItem(item)
    }

    suspend fun updateBasketItem(item: BasketItemEntity) {
        basketDao.updateBasketItem(item)
    }

    suspend fun removeBasketItem(item: BasketItemEntity) {
        basketDao.deleteBasketItem(item)
    }
}
