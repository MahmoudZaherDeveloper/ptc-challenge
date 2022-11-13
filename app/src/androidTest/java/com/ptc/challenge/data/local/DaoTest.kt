package com.ptc.challenge.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.ptc.challenge.data.getOrAwaitValue
import com.ptc.challenge.data.local.entity.ProductEntity
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest

class DaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: Database

    private lateinit var dao: Dao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.dao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertProducts_returnTrue() = runTest {

        val products: List<ProductEntity> = listOf(
            ProductEntity(
                "1",
                "Mobile",
                "Samsung",
                30,
                price = "3000",
                specialPrice = "2500",
                image = "",
                ratingAvg = 5F
            )
        )
        dao.insertProducts(products)
        val product = dao.getProduct("1").getOrAwaitValue()
        assertThat(products).contains(product)
    }


}













