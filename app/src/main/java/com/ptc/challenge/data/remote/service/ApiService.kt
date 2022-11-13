package com.ptc.challenge.data.remote.service

import com.ptc.challenge.data.remote.dto.ConfigurationResponse
import com.ptc.challenge.data.remote.dto.ProductDetailsResponse
import com.ptc.challenge.data.remote.dto.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("configurations/")
    suspend fun configuration(): Response<ConfigurationResponse>


    @GET("search/{q}/page/{page}/")
    suspend fun getProducts(
        @Path("page") pageNo: Int = 1,
        @Path("q") query: String
    ): Response<ProductsResponse>


    @GET("product/{sku}/")
    suspend fun getProduct(
        @Path("sku") sku: String
    ): Response<ProductDetailsResponse>

}