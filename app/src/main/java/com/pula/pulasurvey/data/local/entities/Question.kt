package com.pula.pulasurvey.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
class Question(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "question_id")
    val questionId: String
)
