package com.pula.pulasurvey.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "options",
    indices = [Index(value = ["display_text", "option_value"], unique = true)]
)
data class OptionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val optionId: Int? = null,
    @ColumnInfo(name = "question_id")
    val questionId: String,
    @ColumnInfo(name = "display_text")
    val displayText: String,
    @ColumnInfo(name = "option_value")
    val optionValue: String
)
