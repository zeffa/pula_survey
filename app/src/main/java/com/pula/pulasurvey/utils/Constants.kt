package com.pula.pulasurvey.utils

import com.pula.pulasurvey.utils.Constants.ANSWER_TYPE_FLOAT
import com.pula.pulasurvey.utils.Constants.ANSWER_TYPE_SINGLE_LINE_TEXT
import com.pula.pulasurvey.utils.Constants.QUESTION_TYPE_FREE_TEXT
import com.pula.pulasurvey.utils.Constants.QUESTION_TYPE_SELECT_ONE
import com.pula.pulasurvey.utils.Constants.QUESTION_TYPE_VALUE

object Constants {
    const val BASE_URl = "https://run.mocky.io/v3/"
    const val QUESTION_PREFERENCE_NAME = "question_preferences"
    const val QUESTION_TYPE_SELECT_ONE = "SELECT_ONE"
    const val QUESTION_TYPE_FREE_TEXT = "FREE_TEXT"
    const val QUESTION_TYPE_VALUE = "TYPE_VALUE"
    const val ANSWER_TYPE_SINGLE_LINE_TEXT = "SINGLE_LINE_TEXT"
    const val ANSWER_TYPE_FLOAT = "FLOAT"
}

enum class QuestionType(TYPE: String) {
    FREE_TEXT(QUESTION_TYPE_FREE_TEXT),
    SELECT_ONE(QUESTION_TYPE_SELECT_ONE),
    TYPE_VALUE(QUESTION_TYPE_VALUE)
}

enum class AnswerType(TYPE: String) {
    SINGLE_LINE_TEXT(ANSWER_TYPE_SINGLE_LINE_TEXT),
    FLOAT(ANSWER_TYPE_FLOAT)
}