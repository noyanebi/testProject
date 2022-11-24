package com.test.domain.usecase.base

abstract class UseCase<in R, out T> {
    abstract fun execute(input: R? = null): T
}