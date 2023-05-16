package com.example.raeetrivial.domain

data class QuestionToDisplay (
    val category: String,
    val answers: Array<Answer>,
    val difficulty: String,
    val question: String,
    val type: String,
        )