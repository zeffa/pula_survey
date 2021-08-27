package com.pula.pulasurvey.ui.models

data class Question(
    val questionId: String,
    val questionText: String,
    val questionType: String,
    val answerType: String,
    val nextQuestionId: String,
    var options: List<Option> = emptyList()
)
