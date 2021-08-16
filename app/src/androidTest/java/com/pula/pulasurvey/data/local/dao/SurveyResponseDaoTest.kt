package com.pula.pulasurvey.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.pula.pulasurvey.data.local.PulaSurveyDatabase
import com.pula.pulasurvey.data.local.entities.CompletedSurveyEntity
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
class SurveyResponseDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: PulaSurveyDatabase
    private lateinit var responseDao: CompletedSurveyDao

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            appContext,
            PulaSurveyDatabase::class.java
        ).allowMainThreadQueries().build()
        responseDao = database.completedSurveyDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertResponseToDatabase() = runBlockingTest {
        val answer = CompletedSurveyEntity(
            answer = "Farmer Name",
            questionId = "id",
            id = 1
        )
        responseDao.insert(answer)
        val allResponses = responseDao.getCompletedSurvey()
        assertThat(allResponses).contains(answer)
    }

    @Test
    fun testClearResponsesFromDb() = runBlockingTest {
        val answer = CompletedSurveyEntity(
            answer = "Farmer Name",
            questionId = "id"
        )
        responseDao.insert(answer)
        responseDao.clearAll()
        val allResponses = responseDao.getCompletedSurvey()
        assertThat(allResponses).isEmpty()
    }
}