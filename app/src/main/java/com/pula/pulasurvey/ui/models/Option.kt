package com.pula.pulasurvey.ui.models

data class Option(
    val optionId: Int,
    val questionId: String,
    val displayText: String,
    val optionValue: String
)
