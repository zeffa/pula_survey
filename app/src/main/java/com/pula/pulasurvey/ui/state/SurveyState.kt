package com.pula.pulasurvey.ui.state

import com.pula.pulasurvey.data.remote.dto.SurveyDTO

sealed class SurveyState {
    object LOADING : SurveyState()
    class FETCHED(surveyDTO: SurveyDTO) : SurveyState()
    class FAILED(error: String) : SurveyState()
}
