package com.ptc.challenge.domain.preferences


interface Preferences {
    suspend fun saveCurrencySymbol(symbol: String)
    suspend fun getCurrencySymbol(): String?


    companion object {
        const val PREF_NAME = "configuration"
        const val KEY_CURRENCY_SYMBOL = "currency_symbol"

    }
}