package com.ptc.challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptc.challenge.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class Database: RoomDatabase() {

    abstract fun dao(): Dao
}