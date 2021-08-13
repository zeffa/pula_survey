package com.pula.pulasurvey.di

import android.content.Context
import androidx.room.Room
import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            PulaSurveyDatabase::class.java,
            PulaSurveyDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
}