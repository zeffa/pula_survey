package com.pula.pulasurvey.di

import com.pula.pulasurvey.data.datasource.LocalDataSource
import com.pula.pulasurvey.data.datasource.RemoteDataSource
import com.pula.pulasurvey.data.remote.NetworkHelper
import com.pula.pulasurvey.data.remote.PulaSurveyApi
import com.pula.pulasurvey.data.repositories.SurveyRepository
import com.pula.pulasurvey.data.repositories.SurveyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideSurveyRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SurveyRepository {
        return SurveyRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSource()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        api: PulaSurveyApi,
        networkHelper: NetworkHelper
    ): RemoteDataSource {
        return RemoteDataSource(api, networkHelper)
    }

}