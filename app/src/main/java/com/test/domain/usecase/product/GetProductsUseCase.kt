package com.test.domain.usecase.product

import android.app.Application
import com.test.data.impl.local.ProductLocalRepositoryImp
import com.test.data.impl.remote.ProductRemoteRepositoryImp
import com.test.data.model.Product
import com.test.data.remote.errorhandle.ErrorHandler
import com.test.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val context: Application,
    private val productRemoteRepository : ProductRemoteRepositoryImp,
    private val productLocalRepository: ProductLocalRepositoryImp,
    errorHandler: ErrorHandler
) : SingleUseCase<Any?, List<Product>>(errorHandler) {



    override fun execute(input: Any?): Single<List<Product>> {


        //return  if (!ConnectivityUtil.isConnected(context))
        // productLocalRepository.getProducts()
        //        else
        return productRemoteRepository.getProducts()


    }




}