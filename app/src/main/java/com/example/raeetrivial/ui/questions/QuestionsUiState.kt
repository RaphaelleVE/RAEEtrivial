package com.example.raeetrivial.ui.questions

import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.network.model.Result

data class QuestionsUiState(
    var questions: List<Result>,
    var currentQuestion : String,
    var answers: List<Answer>
)