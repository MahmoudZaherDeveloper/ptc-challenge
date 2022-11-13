package com.ptc.challenge.domain.model

import com.ptc.challenge.domain.preferences.Preferences
import javax.inject.Inject


data class ProductDetails(
    val sku: String,
    val name: String,
    val brand: String,
    val savingPercentage: Int,
    val price: String,
    val specialPrice: String,
    val image: String? = null,
    val ratingAvg: Float,
    val description: String? = null,
    val ratingsTotal: Int? = null,
    val images: List<String>? = null,
    val sellerName: String? = null,
    val deliveryTime: String? = null,

    )
