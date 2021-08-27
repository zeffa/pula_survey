package com.pula.pulasurvey.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionAndOptions(
    @Embedded
    val question: QuestionEntity,
    @Relation(parentColumn = "question_id", entityColumn = "question_id")
    val options: List<OptionEntity>
)
