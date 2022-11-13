package com.ptc.challenge.data.prefrances

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.ptc.challenge.domain.preferences.Preferences
import com.ptc.challenge.domain.preferences.Preferences.Companion.KEY_CURRENCY_SYMBOL
import com.ptc.challenge.domain.preferences.Preferences.Companion.PREF_NAME
import kotlinx.coroutines.flow.first

class PreferencesImpl(context: Context) : Preferences {

    private val dataStore = context.createDataStore(name = PREF_NAME)
    private val dataStoreKey = preferencesKey<String>(KEY_CURRENCY_SYMBOL)

    override suspend fun saveCurrencySymbol(symbol: String) {

        dataStore.edit {
            it[dataStoreKey] = symbol

        }
    }

    override suspend fun getCurrencySymbol(): String? {
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }


}