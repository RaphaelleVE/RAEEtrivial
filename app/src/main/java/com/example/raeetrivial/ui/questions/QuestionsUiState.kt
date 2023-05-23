package com.example.raeetrivial.ui.questions

import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.domain.QuestionsOfTheDay

data class QuestionsUiState(
    var questions: QuestionsOfTheDay,
    var currentQuestion : String,
    var answers: List<Answer>
)