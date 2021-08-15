package com.pula.pulasurvey.ui.survey

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pula.pulasurvey.R
import com.pula.pulasurvey.databinding.ConductSurveyBinding
import com.pula.pulasurvey.ui.SurveyViewModel
import com.pula.pulasurvey.ui.models.Question
import com.pula.pulasurvey.ui.state.SurveyResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConductSurveyFragment : Fragment(R.layout.conduct_survey) {
    private val viewModel by viewModels<SurveyViewModel>()
    private var _binding: ConductSurveyBinding? = null
    private val binding: ConductSurveyBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ConductSurveyBinding.bind(view)
        viewModel.fetchSurveyQuestions()

        viewModelObservers()
    }

    private fun viewModelObservers() {
        viewModel.surveyResource.observe(viewLifecycleOwner) { state ->
            when(state) {
                is SurveyResource.Loading -> {

                }
                is SurveyResource.InternetUnavailable -> {

                }
                is SurveyResource.InternetAvailable -> {

                }
                is SurveyResource.LoadingSuccess -> {
                    composeSurveyViews(state.questions)
                }
                is SurveyResource.LoadingFailed -> {

                }
            }
        }
    }

    private fun composeSurveyViews(questions: List<Question>) {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConductSurveyFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}