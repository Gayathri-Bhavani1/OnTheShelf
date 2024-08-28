package com.paltech.ontheshelf.data.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Double,
    var quantity:Int,
    val discount: Int,
    val imageResId: Int,
    val isFavorite: Boolean,
    val description: String
)
