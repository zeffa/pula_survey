package com.pula.pulasurvey.data.mappers

import com.pula.pulasurvey.data.local.entities.OptionEntity
import com.pula.pulasurvey.data.remote.dto.OptionDTO
import com.pula.pulasurvey.ui.models.Option

abstract class OptionMapper : SurveyDataMapper<OptionDTO, Option, OptionEntity>()

class OptionMapperImpl : OptionMapper() {
    override fun fromDTOToEntity(dto: OptionDTO): OptionEntity {
        return OptionEntity(
            displayText = dto.displayText,
            optionValue = dto.value,
            questionId = dto.questionId ?: ""
        )
    }

    override fun dtoListToEntityList(dtoList: List<OptionDTO>): List<OptionEntity> {
        return dtoList.map { optionDTO ->
            fromDTOToEntity(optionDTO)
        }
    }

    override fun fromEntityToModel(entity: OptionEntity): Option {
        return Option(
            optionId = entity.optionId?:0,
            questionId = entity.questionId,
            displayText = entity.displayText,
            optionValue = entity.optionValue
        )
    }

    override fun entityListToDomainModelList(entityList: List<OptionEntity>): List<Option> {
        return entityList.map {
            fromEntityToModel(it)
        }
    }
}