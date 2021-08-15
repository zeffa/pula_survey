package com.pula.pulasurvey.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Relation

data class QuestionAndOptions(
    @ColumnInfo(name = "question_id")
    val questionId: String,
    @ColumnInfo(name = "question_text")
    val questionText: String,
    @ColumnInfo(name = "question_type")
    val questionType: String,
    @ColumnInfo(name = "answer_type")
    val answerType: String,
    @ColumnInfo(name = "next_question_id")
    val nextQuestionId: String,
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val options: List<OptionEntity>
)
