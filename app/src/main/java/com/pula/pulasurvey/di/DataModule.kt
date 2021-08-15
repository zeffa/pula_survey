package com.pula.pulasurvey.di

import com.pula.pulasurvey.data.datasource.LocalDataSource
import com.pula.pulasurvey.data.datasource.LocalDataSourceImpl
import com.pula.pulasurvey.data.datasource.RemoteDataSource
import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.mappers.OptionMapper
import com.pula.pulasurvey.data.mappers.QuestionMapper
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
        remoteDataSource: RemoteDataSource,
        questionMapper: QuestionMapper,
        optionMapper: OptionMapper
    ): SurveyRepository {
        return SurveyRepositoryImpl(localDataSource, remoteDataSource, questionMapper, optionMapper)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(database: PulaSurveyDatabase): LocalDataSource {
        return LocalDataSourceImpl(database)
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