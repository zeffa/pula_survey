package com.pula.pulasurvey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pula.pulasurvey.data.local.entities.Question

@Database(version = PulaSurveyDatabase.DATABASE_VERSION, entities = [Question::class])
abstract class PulaSurveyDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "pula_survey_db"
    }
}