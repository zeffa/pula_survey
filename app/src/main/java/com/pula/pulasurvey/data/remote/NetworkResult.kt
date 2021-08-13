package com.pula.pulasurvey.data.remote

sealed class NetworkResult<out T> {
    class Success<out T>(val value: T) : NetworkResult<T>()
    class NetworkError(val error: ErrorResponse) : NetworkResult<Nothing>()
    class ServerError(val error: ErrorResponse? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}
