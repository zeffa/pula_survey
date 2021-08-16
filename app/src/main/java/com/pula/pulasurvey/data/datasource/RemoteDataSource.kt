package com.pula.pulasurvey.data.datasource

import com.pula.pulasurvey.data.remote.NetworkHelper
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.PulaSurveyApi
import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import com.pula.pulasurvey.data.remote.dto.SurveyResponse
import com.pula.pulasurvey.data.remote.dto.SurveyResponseDTO
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun sendSurvey(responses: List<SurveyResponseDTO>): NetworkResult<SurveyResponse>
    suspend fun fetchSurveyFromApi(): NetworkResult<SurveyDTO>
}

class RemoteDataSourceImpl @Inject constructor(
    private val api: PulaSurveyApi,
    private val networkHelper: NetworkHelper
) : RemoteDataSource {

    override suspend fun sendSurvey(responses: List<SurveyResponseDTO>): NetworkResult<SurveyResponse> {
        return networkHelper.call {
            api.sendSurvey()
        }
    }

    override suspend fun fetchSurveyFromApi(): NetworkResult<SurveyDTO> {
        return networkHelper.call {
            api.getSurvey()
        }
    }
}