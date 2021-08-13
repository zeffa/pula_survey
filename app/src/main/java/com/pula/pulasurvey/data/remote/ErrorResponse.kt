package com.pula.pulasurvey.data.remote

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val statusCode: Int,
    val message: String
)