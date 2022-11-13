package com.ptc.challenge.domain.model

import com.ptc.challenge.domain.preferences.Preferences
import javax.inject.Inject


data class Product(
    val sku: String,
    val name: String,
    val brand: String,
    val savingPercentage: Int,
    val price: String,
    val specialPrice: String,
    val image: String? = null,
    val ratingAvg: Float,
)
