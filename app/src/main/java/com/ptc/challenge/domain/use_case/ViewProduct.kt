package com.ptc.challenge.domain.use_case

import com.ptc.challenge.data.mapper.toProduct
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.domain.model.ProductDetails
import com.ptc.challenge.domain.preferences.Preferences
import com.ptc.challenge.domain.repository.Repository
import com.ptc.challenge.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ViewProduct @Inject constructor(
    private val repository: Repository,
    private val preferences: Preferences

) {
    suspend operator fun invoke(sku: String): Flow<Resource<ProductDetails>> = flow {
        try {
            when (val results = repository.getProduct(sku)) {
                is Resource.Error -> {
                    emit(Resource.Error(results.exception ?: Exceptions.ServerError))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())

                }
                is Resource.Success -> {
                    emit(
                        Resource.Success(
                            results.data!!.metadata.toProduct().copy(
                                specialPrice = preferences.getCurrencySymbol() + results.data.metadata.toProduct().specialPrice,
                                price = preferences.getCurrencySymbol() + results.data.metadata.toProduct().price
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(Exceptions.NoInternet))

        }

    }

}

