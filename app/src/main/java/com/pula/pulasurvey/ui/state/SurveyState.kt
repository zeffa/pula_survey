package com.pula.pulasurvey.ui.state

import com.pula.pulasurvey.data.remote.dto.SurveyDTO
import com.pula.pulasurvey.ui.models.Question

sealed class SurveyState {
    object LOADING : SurveyState()
    class FETCHED(surveyDTO: SurveyDTO) : SurveyState()
    class FAILED(error: String) : SurveyState()
}

sealed class SurveyResource {
    object Initial : SurveyResource()
    object Loading : SurveyResource()
    object InternetUnavailable : SurveyResource()
    object InternetAvailable : SurveyResource()
    data class LoadingSuccess(val question: Question) : SurveyResource()
    data class LoadingFailed(val error: String) : SurveyResource()
}
