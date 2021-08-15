package com.pula.pulasurvey.data.datasource

import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.entities.OptionEntity
import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.data.local.preferences.QuestionDataStore
import com.pula.pulasurvey.data.remote.dto.SurveyDTO

interface LocalDataSource {
    suspend fun saveStartQuestionToPreference(startQnId: String)
    suspend fun saveSurveyData(survey: SurveyDTO)
    suspend fun commit(startQn: String, questions: List<QuestionEntity>, options: List<OptionEntity>)
}

class LocalDataSourceImpl(
    private val database: PulaSurveyDatabase,
    private val dataStore: QuestionDataStore
    ) : LocalDataSource {
    override suspend fun saveStartQuestionToPreference(startQnId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveSurveyData(survey: SurveyDTO) {
        TODO("Not Implemented")
    }

    override suspend fun commit(startQn: String, questions: List<QuestionEntity>, options: List<OptionEntity>) {
        database.optionDao().insert(options)
        database.questionDao().insert(questions)
        dataStore.setStartQuestion(startQn)
    }
}