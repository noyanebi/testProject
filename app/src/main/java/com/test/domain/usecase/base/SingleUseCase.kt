package com.test.domain.usecase.base

import com.test.data.model.Product
import com.test.data.remote.errorhandle.ErrorHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<in R, T>(private val apiErrorHandle: ErrorHandler) : UseCase<R, Single<T>>() {

    fun execute(
        compositeDisposable: CompositeDisposable,
        input: R?,
        onResponse: UseCaseResponse<List<Product>>
    ): Disposable {
        return this.execute(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse.onSuccess(it as List<Product>)
            }, {
                onResponse.onError(it, apiErrorHandle.traceErrorException(it))
            }).also {
                compositeDisposable.add(it)
            }
    }
}