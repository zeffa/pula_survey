package com.pula.pulasurvey.data.repositories

import com.pula.pulasurvey.data.datasource.LocalDataSource
import com.pula.pulasurvey.data.datasource.RemoteDataSource
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.dto.SurveyDTO

class SurveyRepositoryImpl(
    localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SurveyRepository {
    override suspend fun fetchSurveyFromRemote(): NetworkResult<SurveyDTO> {
        return remoteDataSource.fetchSurveyFromApi()
    }

    override suspend fun fetchSurveyFromDb() {
        TODO("Not yet implemented")
    }
}