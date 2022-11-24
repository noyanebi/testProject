package com.test.presentation.base

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val showProgress: ObservableField<Boolean> = ObservableField()
    val networkError: ObservableField<Boolean> = ObservableField()
    val commonMessage: MutableLiveData<String> = MutableLiveData()
    val commonMessageResource: MutableLiveData<Int> = MutableLiveData()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val errorTextMessage: ObservableField<String> = ObservableField()

    init {
        showProgress.set(false)
        networkError.set(false)
        errorTextMessage.set("خطا در اتصال به اینترنت")
    }

    open fun showProgressBar() {
        showProgress.set(true)
    }

    open fun hideProgressBar() {
        showProgress.set(false)

    }

    open fun showConnectionError() {
        hideProgressBar()
        networkError.set(true)
    }

    open fun hideConnectionError() {
        networkError.set(false)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun setErrorMessage(message: String) {
        errorTextMessage.set(message)
    }
}