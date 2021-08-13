package com.pula.pulasurvey.utils

object CaseConverter {
    private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
    private val snakeRegex = "_[a-zA-Z]".toRegex()

    fun String.toSnakeCase(): String {
        return camelRegex.replace(this) { matcher ->
            "_${matcher.value}"
        }.lowercase()
    }

    fun String.toLowerCamelCase(): String =
        split('_').joinToString("", transform = {
            it.replaceFirstChar(Char::uppercase)
        }).replaceFirstChar(Char::lowercase)

}