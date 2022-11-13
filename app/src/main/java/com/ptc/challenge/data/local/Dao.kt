package com.ptc.challenge.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.ptc.challenge.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Delete
    suspend fun deleteProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM productEntity")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query(
        """
            SELECT *
            FROM productEntity
            WHERE sku = :sku
        """
    )
    fun getProduct(sku: String): LiveData<ProductEntity>
}
