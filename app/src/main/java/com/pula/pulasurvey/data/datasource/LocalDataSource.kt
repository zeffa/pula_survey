package com.pula.pulasurvey.data.datasource

import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.entities.CompletedSurveyEntity
import com.pula.pulasurvey.data.local.entities.OptionEntity
import com.pula.pulasurvey.data.local.entities.QuestionAndOptions
import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.data.local.preferences.QuestionDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

interface LocalDataSource {
    suspend fun getPendingResponsesFromDb(): List<CompletedSurveyEntity>
    suspend fun getStartQuestion(): String
    suspend fun checkIfSurveySavedInDb(): Boolean
    suspend fun fetchQuestionsAndOptionFromDb(): Flow<List<QuestionAndOptions>>
    suspend fun insertSurveyResponseToDb(responseEntities: List<CompletedSurveyEntity>): List<Long>
    suspend fun saveStartQuestionId(startQnId: String)
    suspend fun clearResponses()
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

    override suspend fun getPendingResponsesFromDb(): List<CompletedSurveyEntity> {
        return database.completedSurveyDao().getCompletedSurvey()
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
            async { dataStore.setStartQuestion(startQn) }
            async { database.optionDao().insert(options) }
            async { database.questionDao().insert(questions) }
        }
    }

    override suspend fun checkIfSurveySavedInDb(): Boolean {
        return database.questionDao().count().first() > 0
    }

    override suspend fun fetchQuestionsAndOptionFromDb(): Flow<List<QuestionAndOptions>> {
        return database.questionDao().getQuestionsAndOptionFromDb()
    }

    override suspend fun insertSurveyResponseToDb(responseEntities: List<CompletedSurveyEntity>): List<Long> {
        return database.completedSurveyDao().insert(responseEntities)
    }

    override suspend fun saveStartQuestionId(startQnId: String) {
        dataStore.setStartQuestion(startQnId)
    }

    override suspend fun clearResponses() {
        database.completedSurveyDao().clearAll()
    }
}