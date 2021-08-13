package com.pula.pulasurvey.utils

import androidx.test.platform.app.InstrumentationRegistry
import com.pula.pulasurvey.PulaTestApplication
import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun stringFromFile(fileName: String) : String {
        try {
            val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as PulaTestApplication).assets.open(fileName)
            return buildString {
                val reader = InputStreamReader(inputStream, "UTF-8")
                reader.readLines().forEach {
                    this.append(it)
                }
            }
        }catch (e: IOException){
            throw e
        }
    }
}