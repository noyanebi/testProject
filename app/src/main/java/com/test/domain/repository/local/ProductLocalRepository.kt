package com.test.domain.repository.local


import com.test.data.local.database.product.ProductEntity
import io.reactivex.Single

interface ProductLocalRepository {
    fun getProducts(): Single<List<ProductEntity>>

    fun getSubProducts(id: String): Single<List<ProductEntity>>

    fun insert(product: List<ProductEntity>)

}