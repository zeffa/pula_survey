package com.pula.pulasurvey.data.datasource

import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.entities.OptionEntity
import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.data.remote.dto.SurveyDTO

interface LocalDataSource {
    suspend fun saveSurveyData(survey: SurveyDTO)
    suspend fun commit(questions: List<QuestionEntity>, options: List<OptionEntity>)
}

class LocalDataSourceImpl(private val database: PulaSurveyDatabase) : LocalDataSource {
    override suspend fun saveSurveyData(survey: SurveyDTO) {
        TODO("Not Implemented")
    }

    override suspend fun commit(questions: List<QuestionEntity>, options: List<OptionEntity>) {
        database.optionDao().insert(options)
        database.questionDao().insert(questions)
    }
}