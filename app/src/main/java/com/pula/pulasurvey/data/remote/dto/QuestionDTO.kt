package com.pula.pulasurvey.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuestionDTO(
    @SerializedName("id")
    val questionId: String,
    @SerializedName("question_type")
    val questionType: String,
    @SerializedName("answer_type")
    val answerType: String,
    @SerializedName("question_text")
    var questionText: String,
    @SerializedName("options")
    val options: List<OptionDTO>? = listOf(),
    @SerializedName("next")
    val nextQuestion: String? = null,
    var surveyId: String? = null
)
