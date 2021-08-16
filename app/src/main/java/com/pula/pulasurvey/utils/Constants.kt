package com.pula.pulasurvey.utils

object Constants {
    const val BASE_URl = "https://run.mocky.io/v3/"
    const val QUESTION_PREFERENCE_NAME = "question_preferences"
    const val QUESTION_TYPE_SELECT_ONE = "SELECT_ONE"
    const val QUESTION_TYPE_FREE_TEXT = "FREE_TEXT"
    const val QUESTION_TYPE_VALUE = "TYPE_VALUE"
    const val ANSWER_TYPE_SINGLE_LINE_TEXT = "SINGLE_LINE_TEXT"
    const val ANSWER_TYPE_FLOAT = "FLOAT"
}

enum class QuestionType {
    FREE_TEXT, SELECT_ONE, TYPE_VALUE
}

enum class AnswerType {
    SINGLE_LINE_TEXT, FLOAT
}