package com.pula.pulasurvey.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.repositories.SurveyRepository
import com.pula.pulasurvey.ui.models.CompletedSurvey
import com.pula.pulasurvey.ui.models.Question
import com.pula.pulasurvey.ui.state.SurveyResource
import com.pula.pulasurvey.utils.NetworkStatus
import com.pula.pulasurvey.utils.NetworkStatusHelper
import com.pula.pulasurvey.utils.SendSurveyResponseToServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val networkStatusHelper: NetworkStatusHelper
) : ViewModel() {
    private var answersList = mutableListOf<CompletedSurvey>()

    private var _surveyQuestions: MutableLiveData<List<Question>> = MutableLiveData()

    private var _surveyResource: MutableLiveData<SurveyResource> = MutableLiveData()

    val surveyResource: LiveData<SurveyResource> get() = _surveyResource
    private fun fetchSurveyFromApi() {
        viewModelScope.launch {
            networkStatusHelper.observeForever {
                checkInternetConnection(it)
            }
        }
    }

    private fun getSurveyFromServer() {
        viewModelScope.launch {
            when (val response = surveyRepository.fetchSurveyFromRemote()) {
                is NetworkResult.Success -> {
                    surveyRepository.saveStartQuestionId(response.value.startQuestionId)
                    if (!surveyRepository.isSurveySavedLocally()) {
                        surveyRepository.saveSurvey(response.value)
                    }
                    collectQuestions()
                }
                is NetworkResult.NetworkError -> {
                    _surveyResource.value = SurveyResource.LoadingFailed(response.error.message)
                }
                is NetworkResult.ServerError -> {
                    _surveyResource.value = response.error?.message?.let {
                        SurveyResource.LoadingFailed(
                            it
                        )
                    }!!
                }
                else -> Unit
            }
        }
    }

    private fun checkInternetConnection(networkStatus: NetworkStatus) {
        when (networkStatus) {
            NetworkStatus.Connected -> {
                getSurveyFromServer()
            }
            NetworkStatus.Disconnected -> _surveyResource.value =
                SurveyResource.InternetUnavailable
        }
    }

    private fun getStartQuestionId(): String {
        return runBlocking {
            surveyRepository.getStartQuestion()
        }
    }

    fun fetchSurveyQuestions() {
        viewModelScope.launch {
            _surveyResource.value = SurveyResource.Loading
            when (surveyRepository.isSurveySavedLocally()) {
                true -> collectQuestions()
                else -> {
                    fetchSurveyFromApi()
                }
            }
        }
    }

    private suspend fun collectQuestions() {
        surveyRepository.fetchSurveyFromDb().collect { questionOptions ->
            val questions = surveyRepository.formatToQuestionDomain(questionOptions)
            _surveyQuestions.value = questions
            setQuestionsLiveData(questions)
        }
    }

    private fun setQuestionsLiveData(questions: List<Question>) {
        _surveyResource.value = questions.find {
            it.questionId == getStartQuestionId()
        }?.let { SurveyResource.LoadingSuccess(it) }
    }

    fun setNextQuestionId(nextQuestionId: String) {
        viewModelScope.launch {
            _surveyResource.value = SurveyResource.LoadingSuccess(_surveyQuestions.value?.find {
                it.questionId == nextQuestionId
            }!!)
        }
    }

    fun setQuestionResponse(questionId: String?, answer: String) {
        answersList.add(CompletedSurvey(questionId!!, answer))
    }

    fun saveSurveyResponse() {
        viewModelScope.launch {
            surveyRepository.saveSurveyResponse(answersList)
        }
    }

    fun createPeriodWorker(workManager: WorkManager) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val worker = PeriodicWorkRequestBuilder<SendSurveyResponseToServer>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag("surveyUpload")
            .build()
        workManager.enqueueUniquePeriodicWork(
            "periodicSurveyUpload",
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )
    }
}