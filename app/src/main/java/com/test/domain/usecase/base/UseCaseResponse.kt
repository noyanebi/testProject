package com.test.domain.usecase.base

import com.test.data.remote.errorhandle.ErrorModel

interface UseCaseResponse<T> {

    fun onSuccess(value: T)

    fun onError(error: Throwable, errorModel: ErrorModel?)
}