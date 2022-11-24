package com.test.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.local.database.product.ProductDao
import com.test.data.local.database.product.ProductEntity

@Database(entities = [ ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun productDao(): ProductDao
}