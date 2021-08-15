package com.pula.pulasurvey.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.pula.pulasurvey.utils.Constants.QUESTION_PREFERENCE_NAME

val Context.surveyDataStore by preferencesDataStore(
    name = QUESTION_PREFERENCE_NAME
)