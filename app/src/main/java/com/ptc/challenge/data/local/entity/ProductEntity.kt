package com.ptc.challenge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productEntity")
data class ProductEntity(
    @PrimaryKey val sku: String,
    val name: String,
    val brand: String,
    val savingPercentage: Int,
    val price: String,
    val specialPrice: String,
    val image: String? = null,
    val ratingAvg: Float,
)
