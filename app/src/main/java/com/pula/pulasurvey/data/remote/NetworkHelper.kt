package com.pula.pulasurvey.data.remote

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val dispatcher: CoroutineDispatcher) {
    suspend fun<T> call(apiCall: suspend () -> T): NetworkResult<T> {
        return withContext(dispatcher) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        throwable.printStackTrace()
                        NetworkResult.NetworkError(convertIOToErrorResponseObject(throwable))
                    }
                    is HttpException -> {
                        throwable.printStackTrace()
                        Log.d("SurveyErrorResponse", "${throwable.message}")
                        val error: ErrorResponse? = convertErrorToErrorResponseObject(throwable)
                        NetworkResult.ServerError(error)
                    }
                    else -> {
                        throwable.printStackTrace()
                        NetworkResult.ServerError(null)
                    }
                }
            }
        }
    }

    private fun convertErrorToErrorResponseObject(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.charStream().let {
                Gson().fromJson(it, ErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }

    private fun convertIOToErrorResponseObject(exception: IOException): ErrorResponse {
        return when (exception) {
            is ConnectException -> {
                ErrorResponse(
                    statusCode = 0,
                    message = "Network is unreachable"
                )
            }
            is SocketTimeoutException -> {
                ErrorResponse(
                    statusCode = 0,
                    message = "Request timed out"
                )
            }
            else ->
                ErrorResponse(
                    statusCode = 0,
                    message = "An unknown error occurred"
                )
        }
    }
}