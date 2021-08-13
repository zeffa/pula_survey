package com.pula.pulasurvey.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.pula.pulasurvey.MainActivity
import com.pula.pulasurvey.utils.FileReader
import com.pula.pulasurvey.utils.OkHttpProvider
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class SurveyFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                OkHttpProvider.getOkHttpClient()
            )
        )
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun serverSuccessResponse() {
        val scenarioRule = activityRule.scenario
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200)
                    .setBody(FileReader.stringFromFile("survey_response.json"))
            }
        }
    }

    @Test
    fun serverFailedResponse() {
        val scenarioRule = activityRule.scenario
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
    }
}