package com.example.raeetrivial.domain

data class Question(
    val category: String = "",
    val answers: List<Answer> = emptyList(),
    val difficulty: String = "",
    val question: String ="",
    val type: String ="",
        )