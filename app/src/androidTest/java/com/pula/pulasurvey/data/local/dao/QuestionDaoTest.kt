package com.pula.pulasurvey.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.entities.QuestionEntity
import com.pula.pulasurvey.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class QuestionDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: PulaSurveyDatabase
    private lateinit var questionDao: QuestionDao

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            appContext,
            PulaSurveyDatabase::class.java
        ).allowMainThreadQueries().build()
        questionDao = database.questionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertQuestionDatabase() = runBlockingTest {
        val question = QuestionEntity(
            "idOne",
            "surveyId",
            "question text",
            "questionType",
            "answer type",
            "next"
        )
        questionDao.insert(question)
        val allQuestions = questionDao.getQuestionList().asLiveData().getOrAwaitValue()
        assertThat(allQuestions[0]).isEqualTo(question)
        assertThat(allQuestions).contains(question)
    }

    @Test
    fun isQuestionCountGreaterThanZero() = runBlockingTest {
        val question = QuestionEntity(
            "idOne",
            "surveyId",
            "question text",
            "questionType",
            "answer type",
            "next"
        )
        questionDao.insert(question)
        val count = questionDao.count().asLiveData().getOrAwaitValue()
        assertThat(count).isGreaterThan(0)
    }
}