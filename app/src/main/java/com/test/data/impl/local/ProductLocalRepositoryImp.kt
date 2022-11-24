package com.test.data.impl.local


import com.test.data.local.database.product.ProductDao
import com.test.data.local.database.product.ProductEntity
import com.test.domain.repository.local.ProductLocalRepository
import javax.inject.Inject

class ProductLocalRepositoryImp @Inject constructor(private val productDao: ProductDao) :
    ProductLocalRepository {

    override fun getProducts() = productDao.getProducts()

    override fun getSubProducts(id: String) = productDao.getSubProducts(id)

    override fun insert(product: List<ProductEntity>) {
        productDao.insert(product)
    }

}