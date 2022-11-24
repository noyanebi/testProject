package com.test.presentation.product

import androidx.lifecycle.MutableLiveData
import com.test.R
import com.test.data.model.Product
import com.test.domain.usecase.product.GetProductsUseCase
import com.test.presentation.base.BaseViewModel
import com.test.util.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductUseCase: GetProductsUseCase) : BaseViewModel() {

    val itemProducts = MutableLiveData<List<Product>>()
    val image = MutableLiveData<String>()
    val dataLoading = MutableLiveData<Boolean>()
    val mSnackbarText = SnackbarMessage()

    fun start(sub: List<Product>) {
        if(sub.isEmpty())
            mSnackbarText.value = R.string.no_category
        else
        loadTasks(sub)
    }

    private fun loadTasks(sub : List<Product>) {

            itemProducts.value = sub
            image.value = sub[0].image!!

    }
}