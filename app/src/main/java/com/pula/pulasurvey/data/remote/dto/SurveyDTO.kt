package com.pula.pulasurvey.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SurveyDTO(
    @SerializedName("id")
    val surveyId: String,
    @SerializedName("start_question_id")
    val startQuestionId: String,
    @SerializedName("questions")
    val questions: List<QuestionDTO>,
    @SerializedName("strings")
    val strings: StringsDTO
)
