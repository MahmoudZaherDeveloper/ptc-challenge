package com.ptc.challenge.data.remote.dto

data class ProductDto(
    val brand: String,
    val image: String,
    val max_saving_percentage: Int,
    val name: String,
    val price: Int,
    val rating_average: Int,
    val sku: String,
    val special_price: Int
)