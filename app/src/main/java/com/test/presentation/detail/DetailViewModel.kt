package com.test.presentation.detail

import androidx.lifecycle.MutableLiveData
import com.test.presentation.base.BaseViewModel
import com.test.util.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {

    val itemUrl = MutableLiveData<String>()
    val mSnackbarText = SnackbarMessage()
    //private val isDataLoadingError = MutableLiveData<Boolean>()
    //val dataLoading = MutableLiveData<Boolean>()
    fun start(url: String) {




        itemUrl.value = url
    }


}