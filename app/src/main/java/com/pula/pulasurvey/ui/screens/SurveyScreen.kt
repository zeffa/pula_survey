package com.pula.pulasurvey.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pula.pulasurvey.ui.SurveyViewModel
import com.pula.pulasurvey.ui.models.Option
import com.pula.pulasurvey.ui.models.Question
import com.pula.pulasurvey.ui.state.SurveyResource
import com.pula.pulasurvey.ui.theme.PulaDarkBlue
import com.pula.pulasurvey.ui.theme.Shapes
import com.pula.pulasurvey.utils.Constants

@Composable
fun SurveyScreen(
    onFinish: () -> Unit
) {
    val viewModel: SurveyViewModel = hiltViewModel()
    val currentQuestion = viewModel.currentQuestionActive.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            viewModel.surveyResource.observeAsState(initial = SurveyResource.Loading).value.let { state ->
                when (state) {
                    is SurveyResource.Loading -> {
                        viewModel.fetchSurveyQuestions()
                    }
                    is SurveyResource.LoadingFailed -> {
                        Text(text = state.error)
                    }
                    is SurveyResource.LoadingSuccess -> {
                        SurveyUI(
                            currentQuestion.value,
                            onNext = { nextQnId, currentQnId, answer ->
                                run {
                                    viewModel.setNextQuestionId(nextQnId)
                                    viewModel.setQuestionResponse(
                                        currentQnId,
                                        answer
                                    )
                                }
                            }, onDone = { currentQnId, answer ->
                                viewModel.setQuestionResponse(
                                    currentQnId,
                                    answer
                                )
                                viewModel.saveSurveyResponse()
                                onFinish()
                            }
                        )
                    }
                    else -> Box(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun SurveyUI(
    currentQuestion: Question?,
    modifier: Modifier = Modifier,
    onNext: (nextQuestionId: String, currentQuestionId: String, answer: String) -> Unit,
    onDone: (currentQuestionId: String, answer: String) -> Unit
) {
    val textAnswer = remember {
        mutableStateOf("")
    }

    val floatAnswer = remember {
        mutableStateOf("")
    }

    val selectedOption = remember {
        mutableStateOf(Option(0, currentQuestion?.questionId ?: "", "", ""))
    }

    if (currentQuestion == null) {
        return Box {
            Text("")
        }
    }

    if (currentQuestion.questionType == Constants.QUESTION_TYPE_FREE_TEXT) {
        return Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = currentQuestion.questionText)
            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = PulaDarkBlue,
                    focusedBorderColor = PulaDarkBlue,
                    placeholderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                value = textAnswer.value,
                onValueChange = { textAnswer.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = modifier.then(
                    Modifier
                        .focusTarget()
                ),
                keyboardActions = KeyboardActions(onNext = { defaultKeyboardAction(ImeAction.Next) })
            )
            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            if (currentQuestion.nextQuestionId.isNotEmpty()) {
                NextFinishButton(onNext = {
                    onNext(
                        currentQuestion.nextQuestionId,
                        currentQuestion.questionId,
                        textAnswer.value
                    )
                })
            } else {
                NextFinishButton(onDone = {
                    onDone(
                        currentQuestion.questionId,
                        textAnswer.value
                    )
                })
            }
        }
    }

    if (currentQuestion.questionType == Constants.QUESTION_TYPE_SELECT_ONE) {
        return Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            selectedOption.value = radioGroup(
                radioOptions = currentQuestion.options,
                title = currentQuestion.questionText
            )
            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            if (currentQuestion.nextQuestionId.isNotEmpty()) {
                NextFinishButton(onNext = {
                    onNext(
                        currentQuestion.nextQuestionId,
                        currentQuestion.questionId,
                        selectedOption.value.optionValue
                    )
                })
            } else {
                NextFinishButton(onDone = {
                    onDone(
                        currentQuestion.questionId,
                        selectedOption.value.optionValue
                    )
                })
            }
        }
    }
    if (currentQuestion.questionType == Constants.QUESTION_TYPE_VALUE) {
        return Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = currentQuestion.questionText)
            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = PulaDarkBlue,
                    focusedBorderColor = PulaDarkBlue,
                    placeholderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                value = floatAnswer.value,
                onValueChange = { floatAnswer.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = modifier.then(
                    Modifier.focusTarget()
                ),
                keyboardActions = KeyboardActions(onNext = { defaultKeyboardAction(ImeAction.Next) })
            )
            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            if (currentQuestion.nextQuestionId.isNotEmpty()) {
                NextFinishButton(onNext = {
                    onNext(
                        currentQuestion.nextQuestionId,
                        currentQuestion.questionId,
                        floatAnswer.value
                    )
                })
            } else {
                NextFinishButton(onDone = {
                    onDone(
                        currentQuestion.questionId,
                        floatAnswer.value
                    )
                })
            }
        }
    }
}

@Composable
fun NextFinishButton(onNext: (() -> Unit?)? = null, onDone: (() -> Unit?)? = null) {
    return Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .border(color = PulaDarkBlue, width = 1.dp, shape = Shapes.medium),
            onClick = {
                if (onNext != null) {
                    onNext()
                }
                if (onDone != null) {
                    onDone()
                }
            }
        ) {
            Text(
                text = if (onDone != null) "FINISH" else "NEXT",
                style = TextStyle(fontWeight = FontWeight.Bold, color = PulaDarkBlue)
            )
        }
    }
}

@Composable
fun radioGroup(
    radioOptions: List<Option> = listOf(),
    title: String = ""
): Option {
    if (radioOptions.isNotEmpty()) {
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions[0])
        }
        Column(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
            )
            radioOptions.forEach { item ->
                Row(
                    Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (item == selectedOption),
                        onClick = { onOptionSelected(item) }
                    )
                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) { append("  ${item.displayText} ") }
                    }

                    ClickableText(
                        text = annotatedString,
                        onClick = {
                            onOptionSelected(item)
                        }
                    )
                }
            }
        }
        return selectedOption
    } else {
        return Option(0, "", "", "")
    }
}

@Composable
@Preview(showBackground = true)
fun SurveyPreview() {
    SurveyScreen(onFinish = {})
}