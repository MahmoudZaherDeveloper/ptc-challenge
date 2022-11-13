package com.ptc.challenge.data.mapper

import com.ptc.challenge.data.remote.dto.ProductDto
import com.ptc.challenge.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        sku = sku,
        name = name,
        image = image,
        brand = brand,
        ratingAvg = rating_average.toFloat(),
        price = price.toString(),
        specialPrice = special_price.toString(),
        savingPercentage = max_saving_percentage,
    )
}