package com.pula.pulasurvey.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_responses")
data class CompletedSurveyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "question_id")
    val questionId: String,
    @ColumnInfo(name = "answer")
    val answer: String
)
