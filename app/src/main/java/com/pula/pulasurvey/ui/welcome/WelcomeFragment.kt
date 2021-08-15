package com.pula.pulasurvey.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pula.pulasurvey.R
import com.pula.pulasurvey.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeBinding.bind(view)
        binding.btnStartSurvey.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToConductSurveyFragment()
            findNavController().navigate(direction)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WelcomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}