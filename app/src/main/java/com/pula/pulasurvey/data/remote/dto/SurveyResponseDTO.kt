package com.pula.pulasurvey.data.remote.dto

data class SurveyResponseDTO(
    val id: Int? = null,
    val questionId: String,
    val answer: String
)
