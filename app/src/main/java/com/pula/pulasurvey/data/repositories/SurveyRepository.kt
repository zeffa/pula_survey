package com.pula.pulasurvey.data.repositories

import com.pula.pulasurvey.data.local.entities.QuestionAndOptions
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import com.pula.pulasurvey.ui.models.CompletedSurvey
import com.pula.pulasurvey.ui.models.Question
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun fetchSurveyFromRemote() : NetworkResult<SurveyDTO>
    suspend fun fetchSurveyFromDb() : Flow<List<QuestionAndOptions>>
    suspend fun saveSurvey(surveyDTO: SurveyDTO)
    suspend fun getStartQuestion() : String
    suspend fun isSurveySavedLocally(): Boolean
    suspend fun formatToQuestionDomain(questions: List<QuestionAndOptions>) : List<Question>
    suspend fun saveSurveyResponse(answersList: MutableList<CompletedSurvey>): List<Long>
}