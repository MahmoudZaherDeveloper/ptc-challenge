package com.ptc.challenge.data.remote.dto

data class Currency(
    val currency_symbol: String,
    val decimals: Int,
    val decimals_sep: String,
    val iso: String,
    val position: Int,
    val thousands_sep: String
)