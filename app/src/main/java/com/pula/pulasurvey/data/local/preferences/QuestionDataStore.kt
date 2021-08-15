package com.pula.pulasurvey.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pula.pulasurvey.data.local.preferences.QuestionDataStoreImpl.PreferencesKeys.START_QUESTION
import com.pula.pulasurvey.utils.surveyDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface QuestionDataStore {
    suspend fun setStartQuestion(startQnId: String)

    fun getStartQuestion(): Flow<String>
}

class QuestionDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : QuestionDataStore {
    override suspend fun setStartQuestion(startQnId: String) {
        context.surveyDataStore.edit { preferences ->
            preferences[START_QUESTION] = startQnId
        }
    }

    override fun getStartQuestion(): Flow<String> {
        return context.surveyDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[START_QUESTION] ?: ""
            }
    }

    object PreferencesKeys {
        val START_QUESTION = stringPreferencesKey("start_question_id")
    }
}