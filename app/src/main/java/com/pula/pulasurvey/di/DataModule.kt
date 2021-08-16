package com.pula.pulasurvey.di

import android.content.Context
import com.pula.pulasurvey.data.datasource.LocalDataSource
import com.pula.pulasurvey.data.datasource.LocalDataSourceImpl
import com.pula.pulasurvey.data.datasource.RemoteDataSource
import com.pula.pulasurvey.data.datasource.RemoteDataSourceImpl
import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.preferences.QuestionDataStore
import com.pula.pulasurvey.data.local.preferences.QuestionDataStoreImpl
import com.pula.pulasurvey.data.mappers.OptionMapper
import com.pula.pulasurvey.data.mappers.QuestionMapper
import com.pula.pulasurvey.data.mappers.SurveyResponseMapper
import com.pula.pulasurvey.data.remote.NetworkHelper
import com.pula.pulasurvey.data.remote.PulaSurveyApi
import com.pula.pulasurvey.data.repositories.SurveyRepository
import com.pula.pulasurvey.data.repositories.SurveyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        optionMapper: OptionMapper,
        responseMapper: SurveyResponseMapper
    ): SurveyRepository {
        return SurveyRepositoryImpl(
            localDataSource,
            remoteDataSource,
            questionMapper,
            optionMapper,
            responseMapper
        )
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: PulaSurveyDatabase,
        dataStore: QuestionDataStore
    ): LocalDataSource {
        return LocalDataSourceImpl(database, dataStore)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        api: PulaSurveyApi,
        networkHelper: NetworkHelper
    ): RemoteDataSource {
        return RemoteDataSourceImpl(api, networkHelper)
    }

    @Singleton
    @Provides
    fun provideQuestionDatastore(
        @ApplicationContext context: Context
    ): QuestionDataStore {
        return QuestionDataStoreImpl(context)
    }
}