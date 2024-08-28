package com.paltech.ontheshelf.data.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket")
data class BasketItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageResId: Int,
    val quantity: Int,
    val price: Double
)
