package com.pula.pulasurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pula.pulasurvey.databinding.ActivityMainBinding
import com.pula.pulasurvey.ui.SurveyViewModel
import com.pula.pulasurvey.ui.state.SurveyState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val viewModel by viewModels<SurveyViewModel>()
    private val binding: ActivityMainBinding get() = _binding
//    private val navController: NavController by lazy {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        navHostFragment.navController
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchSurveyFromApi()

        viewModel.surveyState.observe(this){ state ->
            when(state) {
                is SurveyState.LOADING -> {

                }
                is SurveyState.FETCHED -> {
                    Log.d("startQuestionId", viewModel.getStartQuestion())
                }
                is SurveyState.FAILED -> {

                }
            }
        }
    }
}