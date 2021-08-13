package com.pula.pulasurvey.data.remote

import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import retrofit2.http.GET

interface PulaSurveyApi {
    @GET(value = "d628facc-ec18-431d-a8fc-9c096e00709a")
    suspend fun getSurvey() : SurveyDTO
}