package com.test.presentation.home

import androidx.lifecycle.MutableLiveData
import com.test.R
import com.test.data.model.Product
import com.test.data.remote.errorhandle.ErrorModel
import com.test.domain.usecase.base.UseCaseResponse
import com.test.domain.usecase.product.GetProductsUseCase
import com.test.presentation.base.BaseViewModel
import com.test.util.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getProductUseCase: GetProductsUseCase) : BaseViewModel() {

    val itemProducts = MutableLiveData<List<Product>>()
    val dataLoading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()

    private val isDataLoadingError = MutableLiveData<Boolean>()
    val mSnackbarText = SnackbarMessage()

    fun start() {
        loadTasks(true, true)
    }

    private fun loadTasks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            dataLoading.value = true
        }

        getProductUseCase.execute(compositeDisposable,null,  object : UseCaseResponse<List<Product>> {

            override fun onSuccess(value: List<Product>) {
                if (showLoadingUI) dataLoading.value = false
                isDataLoadingError.value = false
                empty.value = value.isEmpty()
                itemProducts.value = value
            }

            override fun onError(error: Throwable, errorModel: ErrorModel?) {
                if (showLoadingUI) dataLoading.value = false
                isDataLoadingError.value = true
                mSnackbarText.value = R.string.error_faild_get_data
            }
        })
    }
}