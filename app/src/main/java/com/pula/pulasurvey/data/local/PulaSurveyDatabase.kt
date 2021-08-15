package com.pula.pulasurvey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pula.pulasurvey.data.local.dao.OptionDao
import com.pula.pulasurvey.data.local.dao.QuestionDao
import com.pula.pulasurvey.data.local.entities.OptionEntity
import com.pula.pulasurvey.data.local.entities.QuestionEntity

@Database(
    version = PulaSurveyDatabase.DATABASE_VERSION,
    entities = [QuestionEntity::class, OptionEntity::class]
)
abstract class PulaSurveyDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "pula_survey_db"
    }

    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao
}