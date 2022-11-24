package com.test.domain.repository.remote


import com.test.data.local.database.product.ProductEntity
import com.test.data.model.Product
import io.reactivex.Single

interface ProductRemoteRepository {
    fun getProducts(): Single<List<Product>>
    fun getProduct(): Single<ProductEntity>
}