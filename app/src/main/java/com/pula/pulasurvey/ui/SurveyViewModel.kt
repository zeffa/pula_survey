package com.pula.pulasurvey.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.repositories.SurveyRepository
import com.pula.pulasurvey.ui.models.Question
import com.pula.pulasurvey.ui.state.SurveyResource
import com.pula.pulasurvey.utils.NetworkStatus
import com.pula.pulasurvey.utils.NetworkStatusHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val networkStatusHelper: NetworkStatusHelper
) : ViewModel() {
    private var _surveyQuestions: MutableLiveData<List<Question>> = MutableLiveData()
    private var _surveyResource: MutableLiveData<SurveyResource> = MutableLiveData()

    val surveyQuestions: LiveData<List<Question>> get() = _surveyQuestions
    val surveyResource: LiveData<SurveyResource> get() = _surveyResource

    private fun fetchSurveyFromApi() {
        viewModelScope.launch {
            networkStatusHelper.observeForever {
                checkInternetConnection(it)
            }
        }
    }

    private fun getSurveyFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = surveyRepository.fetchSurveyFromRemote()) {
                is NetworkResult.Success -> {
                    surveyRepository.saveSurvey(response.value)
                    collectQuestions()
                }
                is NetworkResult.NetworkError -> {
                    _surveyResource.postValue(SurveyResource.LoadingFailed(response.error.message))
                }
                is NetworkResult.ServerError -> {
                    _surveyResource.postValue(response.error?.message?.let {
                        SurveyResource.LoadingFailed(
                            it
                        )
                    })
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
            NetworkStatus.Disconnected -> _surveyResource.postValue(SurveyResource.InternetUnavailable)
        }
    }

    fun getStartQuestion(): String {
        return runBlocking {
            surveyRepository.getStartQuestion()
        }
    }

    fun fetchSurveyQuestions() {
        viewModelScope.launch {
            _surveyResource.postValue(SurveyResource.Loading)
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
            _surveyResource.postValue(SurveyResource.LoadingSuccess(questions))
        }
    }
}