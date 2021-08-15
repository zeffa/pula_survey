package com.pula.pulasurvey.data.mappers

abstract class SurveyDataMapper<DTO, DomainModel, Entity> {
    open fun fromDTOToModel(dto: DTO): DomainModel? = null
    open fun fromModelToDTO(domainModel: DomainModel): DTO? = null
    open fun fromEntityToModel(entity: Entity): DomainModel? = null
    open fun fromModelToEntity(domainModel: DomainModel): Entity? = null
    open fun fromDTOToEntity(dto: DTO): Entity? = null
    open fun entityListToDomainModelList(entityList: List<Entity>): List<DomainModel> = emptyList()
    open fun dtoListToEntityList(dtoList: List<DTO>) : List<Entity> = emptyList()
}