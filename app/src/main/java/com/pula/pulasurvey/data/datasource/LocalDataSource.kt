package com.pula.pulasurvey.data.datasource

import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.entities.OptionEntity
import com.pula.pulasurvey.data.local.entities.QuestionAndOptions
import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.data.local.preferences.QuestionDataStore
import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

interface LocalDataSource {
    suspend fun saveStartQuestionToPreference(startQnId: String)
    suspend fun saveSurveyData(survey: SurveyDTO)
    suspend fun getStartQuestion(): String
    suspend fun checkIfSurveySavedInDb(): Boolean
    suspend fun fetchQuestionsAndOptionFromDb(): Flow<List<QuestionAndOptions>>
    suspend fun commit(
        startQn: String,
        questions: List<QuestionEntity>,
        options: List<OptionEntity>
    )
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

    override suspend fun getStartQuestion(): String {
        return dataStore.getStartQuestion().first()
    }

    override suspend fun commit(
        startQn: String,
        questions: List<QuestionEntity>,
        options: List<OptionEntity>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            async { database.optionDao().insert(options) }
            async { database.questionDao().insert(questions) }
            dataStore.setStartQuestion(startQn)
        }
    }

    override suspend fun checkIfSurveySavedInDb(): Boolean {
        return database.questionDao().count().first() > 0
    }

    override suspend fun fetchQuestionsAndOptionFromDb(): Flow<List<QuestionAndOptions>> {
        return database.questionDao().getQuestionsAndOptionFromDb()
    }
}