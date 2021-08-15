package com.pula.pulasurvey.di

import com.pula.pulasurvey.data.mappers.OptionMapper
import com.pula.pulasurvey.data.mappers.OptionMapperImpl
import com.pula.pulasurvey.data.mappers.QuestionMapper
import com.pula.pulasurvey.data.mappers.QuestionMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideQuestionMapper(): QuestionMapper = QuestionMapperImpl()

    @Singleton
    @Provides
    fun provideOptionMapper(): OptionMapper = OptionMapperImpl()
}