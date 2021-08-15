package com.pula.pulasurvey.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.repositories.SurveyRepository
import com.pula.pulasurvey.ui.state.SurveyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {
    private var _surveyState: MutableLiveData<SurveyState>? = null
    val surveyState: LiveData<SurveyState> get() = _surveyState!!

    fun fetchSurveyFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = surveyRepository.fetchSurveyFromRemote()) {
                is NetworkResult.Success -> {
                    surveyRepository.saveSurvey(response.value)
                    _surveyState?.postValue(SurveyState.FETCHED(response.value))
                    Log.d("RESPONSE_FROM_API", response.value.toString())
                }
                is NetworkResult.NetworkError -> {
                    Log.d("RESPONSE_FROM_API", response.error.message)
                    _surveyState?.postValue(SurveyState.FAILED(response.error.message))
                }
                is NetworkResult.ServerError -> {
                    Log.d("RESPONSE_FROM_API", response.error.toString())
                    _surveyState?.postValue(response.error?.message?.let { SurveyState.FAILED(it) })
                }
                is NetworkResult.Loading -> {
                    _surveyState?.postValue(SurveyState.LOADING)
                }
                else -> Unit
            }
        }
    }
}