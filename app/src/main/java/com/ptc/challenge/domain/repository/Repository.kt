package com.ptc.challenge.domain.repository

import androidx.lifecycle.LiveData
import com.ptc.challenge.data.local.entity.ProductEntity
import com.ptc.challenge.data.remote.dto.ConfigurationResponse
import com.ptc.challenge.data.remote.dto.ProductDetailsResponse
import com.ptc.challenge.data.remote.dto.ProductsResponse
import com.ptc.challenge.domain.model.Currency
import com.ptc.challenge.domain.model.Product
import com.ptc.challenge.utils.Resource

interface Repository {


    /**
     * Gets user configuration to get currency.
     * @return {ConfigurationModel}
     */
    suspend fun configuration(): Resource<ConfigurationResponse>


    /**
     * Gets pagination list of products
     * @param page - {Int} to get page number
     * @return {List<Product>}
     */
    suspend fun getProducts(page: Int, query:String): Resource<ProductsResponse>

    /**
     * Gets product details with sku.
     * @param sku - {String} product identifier for getting product details
     * @return {ProductDetails}
     */
    suspend fun getProduct(sku: String): Resource<ProductDetailsResponse>

    /**
     * Inserts products from remote to local db
     * @param products - {List} list of ProductEntity model
     */
    suspend fun insertProducts(products: List<ProductEntity>)


    /**
     * delete all products from local db
     * @return {List<Product>}
     */
    fun getAllProducts(): LiveData<List<Product>>

    /**
     * Gets product details  from local db with sku.
     * @param sku - {String} product identifier for getting product details
     * @return {ProductDetails}
     */
    fun getProductFromDb(sku: String): LiveData<Product>


}