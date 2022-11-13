package com.ptc.challenge.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ptc.challenge.data.local.Dao
import com.ptc.challenge.data.local.entity.ProductEntity
import com.ptc.challenge.data.mapper.toProduct
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.data.remote.dto.ConfigurationResponse
import com.ptc.challenge.data.remote.dto.ProductDetailsResponse
import com.ptc.challenge.data.remote.dto.ProductsResponse
import com.ptc.challenge.data.remote.service.ApiService
import com.ptc.challenge.domain.model.Product
import com.ptc.challenge.domain.repository.Repository
import com.ptc.challenge.utils.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: Dao?

) : Repository {

    override suspend fun configuration(): Resource<ConfigurationResponse> {
        val response = api.configuration()
        return if (response.isSuccessful && response.body() != null) {
            Resource.Success(response.body()!!)
        } else {
            if (response.code() == 404) {
                Resource.Error(Exceptions.NotFound)

            } else {
                Resource.Error(Exceptions.ServerError)

            }

        }


    }

    override suspend fun getProducts(page: Int, query: String): Resource<ProductsResponse> {
        val response = api.getProducts(pageNo = page, query = query)
        return try {
            if (response.isSuccessful && response.body() != null) {
                if (response.body()!!.success) {
                    Resource.Success(response.body()!!)

                } else {
                    Resource.Error(Exceptions.NotFound)
                }

            } else {
                if (response.code() == 404) {
                    Resource.Error(Exceptions.NotFound)

                } else {
                    Resource.Error(Exceptions.ServerError)

                }

            }
        } catch (e: Exception) {
            Resource.Error(Exceptions.NoInternet)

        }
    }

    override suspend fun getProduct(sku: String): Resource<ProductDetailsResponse> {
        val response = api.getProduct(sku = sku)

        return if (response.body() != null) {
            if (response.body()!!.success) {

                Resource.Success(response.body()!!)

            } else {

                Resource.Error(Exceptions.NotFound)

            }
        } else {
            if (response.code() == 404) {
                Resource.Error(Exceptions.NotFound)

            } else {
                Resource.Error(Exceptions.ServerError)

            }


        }
    }


    override suspend fun insertProducts(products: List<ProductEntity>) {
        dao!!.insertProducts(products.map { it })
    }


    override fun getAllProducts(): LiveData<List<Product>> {
        return dao!!.getAllProducts().map { entities ->
            entities.map { it.toProduct() }
        }
    }


    override fun getProductFromDb(sku: String): LiveData<Product> {
        return dao!!.getProduct(sku).map { it.toProduct() }
    }

}