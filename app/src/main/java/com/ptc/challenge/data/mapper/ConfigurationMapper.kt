package com.ptc.challenge.data.mapper

import com.ptc.challenge.data.remote.dto.ConfigurationResponse
import com.ptc.challenge.domain.model.Currency

fun ConfigurationResponse.toCurrency(): Currency {
    return Currency(
        currencySymbols = metadata.currency.currency_symbol,
    )
}