package com.pula.pulasurvey.data.remote

import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import com.pula.pulasurvey.data.remote.dto.SurveyResponse
import retrofit2.http.GET

interface PulaSurveyApi {
    @GET(value = "d628facc-ec18-431d-a8fc-9c096e00709a")
    suspend fun getSurvey(): SurveyDTO

    @GET(value = "dummy_endpoint")
    suspend fun sendSurvey(): SurveyResponse
}