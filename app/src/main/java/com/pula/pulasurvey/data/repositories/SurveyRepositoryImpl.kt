package com.pula.pulasurvey.data.repositories

import com.pula.pulasurvey.data.datasource.LocalDataSource
import com.pula.pulasurvey.data.datasource.RemoteDataSource
import com.pula.pulasurvey.data.local.entities.QuestionAndOptions
import com.pula.pulasurvey.data.mappers.OptionMapper
import com.pula.pulasurvey.data.mappers.QuestionMapper
import com.pula.pulasurvey.data.mappers.SurveyResponseMapper
import com.pula.pulasurvey.data.remote.NetworkResult
import com.pula.pulasurvey.data.remote.dto.*
import com.pula.pulasurvey.ui.models.CompletedSurvey
import com.pula.pulasurvey.ui.models.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SurveyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val questionMapper: QuestionMapper,
    private val optionMapper: OptionMapper,
    private val responseMapper: SurveyResponseMapper
) : SurveyRepository {
    override suspend fun fetchSurveyFromRemote(): NetworkResult<SurveyDTO> {
        return remoteDataSource.fetchSurveyFromApi()
    }

    override suspend fun fetchSurveyFromDb(): Flow<List<QuestionAndOptions>> {
        return localDataSource.fetchQuestionsAndOptionFromDb()
    }

    override suspend fun saveSurvey(surveyDTO: SurveyDTO) {
        val optionsDTOList = mutableListOf<OptionDTO>()
        val questionDtoList = surveyDTO.questions
        val stringDataDTO = surveyDTO.strings.stringData
        questionDtoList.forEach { questionDTO ->
            questionDTO.surveyId = surveyDTO.surveyId
            questionDTO.questionText = mapQuestionText(questionDTO, surveyDTO.strings.stringData)
            val quizOptions = questionDTO.options
            quizOptions?.forEach { optionDTO ->
                optionDTO.questionId = questionDTO.questionId
                optionDTO.displayText = mapOptionText(optionDTO, stringDataDTO)
            }
            quizOptions?.let { optionsDTOList.addAll(it.toList()) }
        }
        val questionEntities = questionMapper.dtoListToEntityList(questionDtoList)
        val optionEntities = optionMapper.dtoListToEntityList(optionsDTOList)

        localDataSource.commit(surveyDTO.startQuestionId, questionEntities, optionEntities)

    }

    override suspend fun getStartQuestion(): String {
        return withContext(Dispatchers.IO) {
            localDataSource.getStartQuestion()
        }
    }

    override suspend fun isSurveySavedLocally(): Boolean {
        return localDataSource.checkIfSurveySavedInDb()
    }

    override suspend fun formatToQuestionDomain(questions: List<QuestionAndOptions>): List<Question> {
        val questionsList = mutableListOf<Question>()
        questions.forEach { quizOptions ->
            val question = questionMapper.fromEntityToModel(quizOptions.question).also {
                it?.options = optionMapper.entityListToDomainModelList(quizOptions.options)
            }
            if (question != null) {
                questionsList.add(question)
            }
        }
        return questionsList
    }

    override suspend fun saveSurveyResponse(answersList: MutableList<CompletedSurvey>): List<Long> {
        val responseEntities = answersList.map {
            responseMapper.fromModelToEntity(it)!!
        }
        return localDataSource.insertSurveyResponseToDb(responseEntities)
    }

    override suspend fun sendResponseToServer() {
        val entities = localDataSource.getPendingResponsesFromDb().map {
            responseMapper.fromEntityToDTO(it)!!
        }
        remoteDataSource.sendSurvey(entities)
        localDataSource.clearResponses()
    }

    private fun mapQuestionText(currentDTO: QuestionDTO, stringDataDTO: StringDataDTO): String {
        val stringsMap = stringDataDTO.convertToMap()
        return stringsMap[currentDTO.questionText].toString()
    }

    private fun mapOptionText(currentDTO: OptionDTO, stringDataDTO: StringDataDTO): String {
        val stringsMap = stringDataDTO.convertToMap()
        return stringsMap[currentDTO.displayText].toString()
    }
}