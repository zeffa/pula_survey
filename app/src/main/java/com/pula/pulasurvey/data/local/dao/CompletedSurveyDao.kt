package com.pula.pulasurvey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pula.pulasurvey.data.local.entities.CompletedSurveyEntity

@Dao
interface CompletedSurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(completedSurveyEntity: CompletedSurveyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(completedSurveyEntities: List<CompletedSurveyEntity>): List<Long>

    @Query("SELECT * FROM SURVEY_RESPONSES")
    fun getCompletedSurvey(): List<CompletedSurveyEntity>

    @Query("DELETE FROM SURVEY_RESPONSES")
    suspend fun clearAll()
}