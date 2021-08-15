package com.pula.pulasurvey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.pula.pulasurvey.data.local.entities.OptionEntity

@Dao
interface OptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(optionEntity: OptionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(optionEntities: List<OptionEntity>)
}