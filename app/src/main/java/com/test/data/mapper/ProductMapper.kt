package com.test.data.mapper

import com.test.data.local.database.product.ProductEntity
import com.test.data.model.Product
import com.test.data.model.ProductResponse
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun toProductsEntity(productResponse: ProductResponse): List<ProductEntity> {
        return productResponse.categories.map { toProductEntity(it) }
    }

    fun toProductEntity(product: Product) = ProductEntity(
        label = product.label,
        url = product.url,
        image = product.image,

    )



}