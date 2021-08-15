package com.pula.pulasurvey.data.repositories

import android.util.Log
import com.pula.pulasurvey.data.datasource.LocalDataSource
import com.pula.pulasurvey.data.datasource.RemoteDataSource
import com.pula.pulasurvey.data.mappers.OptionMapper
import com.pula.pulasurvey.data.mappers.QuestionMapper
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.dto.OptionDTO
import com.pula.pulasurvey.data.remote.dto.QuestionDTO
import com.pula.pulasurvey.data.remote.dto.StringDataDTO
import com.pula.pulasurvey.data.remote.dto.SurveyDTO

class SurveyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val questionMapper: QuestionMapper,
    private val optionMapper: OptionMapper
) : SurveyRepository {
    override suspend fun fetchSurveyFromRemote(): NetworkResult<SurveyDTO> {
        return remoteDataSource.fetchSurveyFromApi()
    }

    override suspend fun fetchSurveyFromDb() {
        TODO("Not yet implemented")
    }

    override suspend fun saveSurvey(surveyDTO: SurveyDTO) {
        val optionsDTOList = mutableListOf<OptionDTO>()
        val questionDtoList = surveyDTO.questions
        questionDtoList.forEach { questionDTO ->
            questionDTO.surveyId = surveyDTO.surveyId
            questionDTO.questionText = mapQuestionText(questionDTO, surveyDTO.strings.stringData)
            val quizOptions = questionDTO.options
            quizOptions?.forEach { optionDTO ->
                optionDTO.questionId = questionDTO.questionId
            }
            quizOptions?.let { optionsDTOList.addAll(it.toList()) }
        }

        Log.d("QUESTION_DTO_LIST", questionDtoList.toString())
        val questionEntities = questionMapper.dtoListToEntityList(questionDtoList)
        val optionEntities = optionMapper.dtoListToEntityList(optionsDTOList)

        localDataSource.commit(questionEntities, optionEntities)
    }

    private fun mapQuestionText(currentDTO: QuestionDTO, stringDataDTO: StringDataDTO): String {
        val stringsMap = stringDataDTO.convertToMap()
        return stringsMap[currentDTO.questionText].toString()
    }
}