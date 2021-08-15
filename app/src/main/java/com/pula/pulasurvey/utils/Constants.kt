package com.pula.pulasurvey.utils

object Constants {
    const val BASE_URl = "https://run.mocky.io/v3/"
}

enum class QuestionType {
    FREE_TEXT, SELECT_ONE, TYPE_VALUE
}

enum class AnswerType {
    SINGLE_LINE_TEXT, FLOAT
}