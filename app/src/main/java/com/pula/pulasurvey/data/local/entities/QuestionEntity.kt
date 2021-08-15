package com.pula.pulasurvey.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "question_id")
    val questionId: String,
    @ColumnInfo(name = "survey_id")
    val surveyId: String,
    @ColumnInfo(name = "question_text")
    val questionText: String,
    @ColumnInfo(name = "question_type")
    val questionType: String,
    @ColumnInfo(name = "answer_type")
    val answerType: String,
    @ColumnInfo(name = "next_question_id")
    val nextQuestionId: String,
){
    @Ignore
    val options: List<OptionEntity> = emptyList()
}
