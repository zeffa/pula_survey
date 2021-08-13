package com.pula.pulasurvey.data.datasource

import com.pula.pulasurvey.data.remote.NetworkHelper
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.PulaSurveyApi
import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: PulaSurveyApi, private val networkHelper: NetworkHelper) {
    suspend fun fetchSurveyFromApi() : NetworkResult<SurveyDTO> {
        return networkHelper.call {
            api.getSurvey()
        }
    }
}