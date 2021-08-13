package com.pula.pulasurvey.di

import com.pula.pulasurvey.data.remote.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideNetworkHelper(
        dispatcher: CoroutineDispatcher
    ): NetworkHelper {
        return NetworkHelper(dispatcher)
    }
}