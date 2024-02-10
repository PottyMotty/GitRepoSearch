package com.pottymotty.gitreposearch.util

sealed class FuncResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : FuncResult<T>()
    data class Error(val exception: Throwable?) : FuncResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}


fun <T : Any> FuncResult<T>.withoutType(): FuncResult<Unit> {
    return when (this) {
        is FuncResult.Error -> this
        is FuncResult.Success -> FuncResult.Success(Unit)
    }
}
