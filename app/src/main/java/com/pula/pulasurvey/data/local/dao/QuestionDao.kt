package com.pula.pulasurvey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pula.pulasurvey.data.local.entities.QuestionAndOptions
import com.pula.pulasurvey.data.local.entities.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionEntity: QuestionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionEntities: List<QuestionEntity>)

    @Query("SELECT * FROM QUESTIONS")
    fun getQuestionList() : Flow<List<QuestionEntity>>

    @Query("SELECT COUNT(*) FROM QUESTIONS")
    fun count() : Flow<Int>

    @Query("SELECT * FROM QUESTIONS")
    fun getQuestionsAndOptionFromDb() : Flow<List<QuestionAndOptions>>
}