package com.pula.pulasurvey.utils

import com.google.common.truth.Truth.assertThat
import com.pula.pulasurvey.utils.CaseConverter.toLowerCamelCase
import com.pula.pulasurvey.utils.CaseConverter.toSnakeCase
import org.junit.Test

class CaseConverterTest {
    @Test
    fun `convert string from camel case to snake case`() {
        val stringToCovert = "myNameIsZeffah"
        val expectedOutPut = "my_name_is_zeffah"

        assertThat(expectedOutPut).isEqualTo(stringToCovert.toSnakeCase())
    }

    @Test
    fun `convert string from snake case to lower camel case`() {
        val stringToCovert = "my_name_is_zeffah"
        val expectedOutPut = "myNameIsZeffah"

        assertThat(expectedOutPut).isEqualTo(stringToCovert.toLowerCamelCase())
    }
}