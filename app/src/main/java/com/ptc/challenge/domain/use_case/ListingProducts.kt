package com.ptc.challenge.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ptc.challenge.data.remote.datasource.ProductsPagingSource
import com.ptc.challenge.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListingProducts @Inject constructor(
    private val dataSource: ProductsPagingSource
) {

    operator fun invoke(query: String): Flow<PagingData<Product>> {
        dataSource.query = query
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { dataSource }
        ).flow
    }


}