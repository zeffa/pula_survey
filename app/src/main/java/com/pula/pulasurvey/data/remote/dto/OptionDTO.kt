package com.pula.pulasurvey.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OptionDTO(
    @SerializedName("value")
    val value: String,
    @SerializedName("display_text")
    var displayText: String,
    var questionId: String? = null
)
