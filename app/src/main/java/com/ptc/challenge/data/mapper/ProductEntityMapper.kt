package com.ptc.challenge.data.mapper

import com.ptc.challenge.data.local.entity.ProductEntity
import com.ptc.challenge.domain.model.Product

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        sku = sku,
        name = name,
        image = image,
        brand = brand,
        ratingAvg = ratingAvg,
        price = price,
        specialPrice = specialPrice,
        savingPercentage = savingPercentage,
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        sku = sku,
        name = name,
        image = image,
        brand = brand,
        ratingAvg = ratingAvg,
        price = price,
        specialPrice = specialPrice,
        savingPercentage = savingPercentage,
    )
}


