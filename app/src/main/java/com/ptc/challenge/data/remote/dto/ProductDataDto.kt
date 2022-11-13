package com.ptc.challenge.data.remote.dto

data class ProductDataDto(
    val brand: String,
    val image_list: List<String>,
    val max_saving_percentage: Int,
    val name: String,
    val price: Int,
    val rating: Rating,
    val seller_entity: SellerEntity,
    val sku: String,
    val special_price: Int,
    val summary: Summary
)