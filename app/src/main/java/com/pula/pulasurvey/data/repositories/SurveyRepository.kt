package com.pula.pulasurvey.data.repositories

import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.dto.SurveyDTO

interface SurveyRepository {
    suspend fun fetchSurveyFromRemote() : NetworkResult<SurveyDTO>
    suspend fun fetchSurveyFromDb()
}