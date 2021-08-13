package com.pula.pulasurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pula.pulasurvey.databinding.ActivityMainBinding
import com.pula.pulasurvey.ui.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<SurveyViewModel>()
    private lateinit var _binding: ActivityMainBinding
    private val binding: ActivityMainBinding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchSurveyFromApi()
    }
}