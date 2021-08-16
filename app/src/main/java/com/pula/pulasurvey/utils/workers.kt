package com.pula.pulasurvey.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pula.pulasurvey.data.repositories.SurveyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendSurveyResponseToServer @Inject constructor(
    private val repository: SurveyRepository,
    appContext: Context,
    workerParams: WorkerParameters
) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
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