package com.pula.pulasurvey.ui.survey

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pula.pulasurvey.R
import com.pula.pulasurvey.databinding.ConductSurveyBinding

class ConductSurveyFragment : Fragment(R.layout.conduct_survey) {
    private var _binding: ConductSurveyBinding? = null
    private val binding: ConductSurveyBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ConductSurveyBinding.bind(view)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConductSurveyFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}