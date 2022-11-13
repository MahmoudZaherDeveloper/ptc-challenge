package com.ptc.challenge.domain.use_case

import com.ptc.challenge.data.mapper.toCurrency
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.domain.model.Currency
import com.ptc.challenge.domain.preferences.Preferences
import com.ptc.challenge.domain.repository.Repository
import com.ptc.challenge.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class Configuration @Inject constructor(
    private val repository: Repository,
    private val preferences: Preferences
) {

    suspend operator fun invoke(): Flow<Resource<Currency>> = flow {
        try {
            when (val results = repository.configuration()) {
                is Resource.Error -> {
                    emit(Resource.Error(results.exception?: Exceptions.ServerError))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Success -> {
                    preferences.saveCurrencySymbol(results.data!!.toCurrency().currencySymbols)
                    emit(
                        Resource.Success(
                            results.data.toCurrency()
                        )
                    )
                }
            }

        } catch (e: Exception) {
            emit(Resource.Error(Exceptions.NoInternet))

        }
    }


}

