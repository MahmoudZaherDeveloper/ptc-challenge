package com.ptc.challenge.data.mapper

import com.ptc.challenge.data.remote.dto.ProductDataDto
import com.ptc.challenge.domain.model.Product
import com.ptc.challenge.domain.model.ProductDetails
import com.ptc.challenge.domain.preferences.Preferences
import javax.inject.Inject

fun ProductDataDto.toProduct(): ProductDetails {
    return ProductDetails(

        sku = sku,
        name = name,
        brand = brand,
        ratingAvg = rating.average.toFloat(),
        price = price.toString(),
        specialPrice = special_price.toString(),
        savingPercentage = max_saving_percentage,
        images = image_list,
        deliveryTime = seller_entity.delivery_time,
        sellerName = seller_entity.name,
        ratingsTotal = rating.ratings_total,
        description = summary.description

    )
}