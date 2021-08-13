package com.pula.pulasurvey.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StringsDTO(
    @SerializedName("en")
    val stringData: StringDataDTO
)