package com.pula.pulasurvey.data.mappers

import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.data.remote.dto.QuestionDTO
import com.pula.pulasurvey.ui.models.Question

abstract class QuestionMapper : SurveyDataMapper<QuestionDTO, Question, QuestionEntity>()

class QuestionMapperImpl : QuestionMapper() {
    override fun fromDTOToEntity(dto: QuestionDTO): QuestionEntity {
        return QuestionEntity(
            questionId = dto.questionId,
            surveyId = dto.surveyId?:"",
            questionText = dto.questionText,
            questionType = dto.questionType,
            answerType = dto.answerType,
            nextQuestionId = dto.nextQuestion?:""
        )
    }

    override fun dtoListToEntityList(dtoList: List<QuestionDTO>): List<QuestionEntity> {
        return dtoList.map { questionDTO ->
            fromDTOToEntity(questionDTO)
        }
    }
}