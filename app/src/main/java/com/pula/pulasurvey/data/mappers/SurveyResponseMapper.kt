package com.pula.pulasurvey.data.mappers

import com.pula.pulasurvey.data.local.entities.CompletedSurveyEntity
import com.pula.pulasurvey.data.remote.dto.SurveyResponseDTO
import com.pula.pulasurvey.ui.models.CompletedSurvey

abstract class SurveyResponseMapper :
    SurveyDataMapper<SurveyResponseDTO, CompletedSurvey, CompletedSurveyEntity>()

class SurveyResponseMapperImpl : SurveyResponseMapper() {
    override fun fromModelToEntity(domainModel: CompletedSurvey): CompletedSurveyEntity {
        return CompletedSurveyEntity(
            questionId = domainModel.questionId,
            answer = domainModel.answer
        )
    }

    override fun fromEntityToDTO(entity: CompletedSurveyEntity): SurveyResponseDTO {
        return SurveyResponseDTO(
            questionId = entity.questionId,
            answer = entity.answer
        )
    }
}