package com.pula.pulasurvey.data.mappers

import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.data.remote.dto.QuestionDTO
import com.pula.pulasurvey.ui.models.Question

interface Mapper<I, O> {
    fun map(input: I): O
}

interface ListMapper<I, O> : Mapper<List<I>, List<O>>
interface NullableInputListMapper<I, O> : Mapper<List<I>?, List<O>>
interface NullableOutputListMapper<I, O> : Mapper<List<I>, List<O>?>

class QuestionDomainMapper : Mapper<QuestionEntity, Question> {
    override fun map(input: QuestionEntity): Question {
        return Question(
            questionId = input.questionId,
            questionText = input.questionText,
            questionType = input.questionType,
            answerType = input.answerType,
            nextQuestionId = input.nextQuestionId,
            options = emptyList()
        )
    }
}

class QuestionEntityMapper : Mapper<QuestionDTO, QuestionEntity> {
    override fun map(input: QuestionDTO): QuestionEntity {
        return QuestionEntity(
            questionId = input.questionId,
            surveyId = input.surveyId ?: "",
            questionText = input.questionText,
            questionType = input.questionType,
            answerType = input.answerType,
            nextQuestionId = input.nextQuestion ?: ""
        )
    }
}