package com.pula.pulasurvey.utils

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pula.pulasurvey.data.repositories.SurveyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class SendSurveyResponseToServer @AssistedInject constructor(
    private val repository: SurveyRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            sendSurveyToServer(repository)
            Result.success()
        } catch (e: Exception) {
            Result.Retry.failure()
        }
    }

    private fun sendSurveyToServer(repository: SurveyRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.sendResponseToServer()
        }
    }
}