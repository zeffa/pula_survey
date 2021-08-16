package com.pula.pulasurvey.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SurveyResponseDTO(
    val id: Int? = null,
    @SerializedName("question_id")
    val questionId: String,
    val answer: String
)
