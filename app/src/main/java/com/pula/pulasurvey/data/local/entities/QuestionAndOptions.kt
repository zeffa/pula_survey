package com.pula.pulasurvey.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.pula.pulasurvey.ui.models.Question

data class QuestionAndOptions(
    @Embedded
    val question: QuestionEntity,
    @Relation(parentColumn = "question_id", entityColumn = "question_id")
    val options: List<OptionEntity>
) {
//    fun mapToQuestionDomain() : Question {
//        return question.also {
//
//        }
//    }
}
