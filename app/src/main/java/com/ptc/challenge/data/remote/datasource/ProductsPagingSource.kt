package com.ptc.challenge.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ptc.challenge.data.mapper.toProduct
import com.ptc.challenge.data.mapper.toProductEntity
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.domain.model.Product
import com.ptc.challenge.domain.preferences.Preferences
import com.ptc.challenge.domain.repository.Repository
import com.ptc.challenge.utils.Resource
import javax.inject.Inject


private const val STARTING_KEY = 1

class ProductsPagingSource @Inject constructor(
    private val repo: Repository,
    private val preferences: Preferences
) : PagingSource<Int, Product>() {

    //Search query
    var query: String = ""


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val start = params.key ?: STARTING_KEY
        return try {
            when (val results = repo.getProducts(start, query)) {
                is Resource.Error -> {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = when (start) {
                            STARTING_KEY -> null
                            else -> start - 1
                        },
                        nextKey = if (params.key == 2) null else start + 1
                    )
                }
                is Resource.Success -> {
                    repo.insertProducts(results.data!!.metadata.results.map {
                        it.toProduct().toProductEntity()
                    })
                    LoadResult.Page(
                        data = results.data.metadata.results.map {
                            it.toProduct().copy(
                                price = preferences.getCurrencySymbol() + it.price,
                                specialPrice = preferences.getCurrencySymbol() + it.special_price,
                            )
                        },
                        prevKey = when (start) {
                            STARTING_KEY -> null
                            else -> start - 1
                        },
                        nextKey = if (params.key == 2) null else start + 1
                    )
                }
                is Resource.Loading -> {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = when (start) {
                            STARTING_KEY -> null
                            else -> start - 1
                        },
                        nextKey = if (params.key == 2) null else start + 1
                    )
                }
            }
        } catch (e: Exception) {

            LoadResult.Error(throwable = Throwable(Exceptions.ServerError.toString()))

        }

    }


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }


}
