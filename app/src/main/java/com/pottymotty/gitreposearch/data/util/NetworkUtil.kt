package com.pottymotty.gitreposearch.data.util

import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.exceptions.ApiCallFailedException
import com.pottymotty.gitreposearch.util.FuncResult
import java.io.IOException

suspend fun <T : Any, U : Any> NetworkResponse<T, U>.handleResult(
    onSuccess: suspend (T) -> Unit,
    onError: suspend () -> Unit = {},
    onApiError: suspend (HttpResponseCode) -> Unit ={},
): FuncResult<T> {
    return when (this) {
        is NetworkResponse.Success -> {
            onSuccess(this.body)
            FuncResult.Success(this.body)
        }
        is NetworkResponse.ApiError<*> -> {
            onApiError(HttpResponseCode.fromCode(this.code))
            FuncResult.Error(ApiCallFailedException(this.body.toString(),HttpResponseCode.fromCode(this.code)))
        }
        else -> {
            onError()
            FuncResult.Error(IOException())
        }
    }
}

