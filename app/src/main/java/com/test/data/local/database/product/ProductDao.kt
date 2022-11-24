package com.test.data.local.database.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


import io.reactivex.Single

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getProducts(): Single<List<ProductEntity>>


    @Query("SELECT * FROM products WHERE label  LIKE '%' || :id || '%'")
    fun getSubProducts(id: String): Single<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: List<ProductEntity>)

}