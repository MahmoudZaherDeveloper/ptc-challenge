package com.ptc.challenge.data.remote.dto

data class ProductsData(
    val results: List<ProductDto>,
    val sort: String,
    val title: String,
    val total_products: Int
)