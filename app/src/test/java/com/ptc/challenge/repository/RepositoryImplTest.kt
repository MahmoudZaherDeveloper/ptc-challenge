package com.ptc.challenge.repository

import com.google.common.truth.Truth.assertThat
import com.ptc.challenge.data.remote.service.ApiService
import com.ptc.challenge.data.repository.RepositoryImpl
import com.ptc.challenge.remote.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RepositoryImplTest {

    private lateinit var repository: RepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(ApiService::class.java)
        repository = RepositoryImpl(
            api = api,
            dao = null

        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Configuration, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validConfigurationResponse)
        )
        val result = repository.configuration()

        assertThat(result.data != null).isTrue()
    }


    @Test
    fun `Configuration, malformed response, not returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(invalidResponse)
        )
        val result = repository.configuration()

        assertThat(result.exception != null).isTrue()
    }


    @Test
    fun `Products, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validProductsResponse)
        )
        val result = repository.getProducts(1, "phone")

        assertThat(result.data != null).isTrue()
    }


    @Test
    fun `Products, malformed response, not returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)

                .setBody(invalidResponse)
        )
        val result = repository.getProducts(1, "phone")

        assertThat(result.exception != null).isTrue()
    }

    @Test
    fun `Products, not found response, Return not found`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(responseNotFound)
        )
        val result = repository.getProducts(1, "dhasgdhasg")

        assertThat(result.exception != null).isTrue()
    }


    @Test
    fun `Product details, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validProductDetailsResponse)
        )
        val result = repository.getProduct("1")

        assertThat(result.data != null).isTrue()
    }


    @Test
    fun `Product details, malformed response, not returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)

                .setBody(invalidResponse)
        )
        val result = repository.getProduct("1")

        assertThat(result.exception != null).isTrue()
    }

    @Test
    fun `Product details, not found response, Return not found`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)

                .setBody(responseNotFound)
        )
        val result = repository.getProduct("1")

        assertThat(result.exception != null).isTrue()
    }
}