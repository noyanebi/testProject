package com.test.data.impl.remote

import com.test.data.local.database.product.ProductEntity
import com.test.data.mapper.ProductMapper
import com.test.data.model.Product
import com.test.data.remote.APIService
import com.test.domain.repository.remote.ProductRemoteRepository
import io.reactivex.Single
import javax.inject.Inject

class ProductRemoteRepositoryImp @Inject constructor(
    private val apiService: APIService,
    private var productMapper: ProductMapper
) : ProductRemoteRepository {

    override fun getProducts(): Single<List<Product>> {
        return apiService.getProducts()
            .map {
                it.body()?.categories
            /*it.body()?.categories?.map {
                    productMapper.toProductEntity(it)

                }*/

            }
    }

    override fun getProduct(): Single<ProductEntity> {
        return apiService.getProduct()
            .map {
                productMapper.toProductEntity(it.body()!!)
            }
    }


}