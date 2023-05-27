package com.example.raeetrivial.domain

data class User (
    var email: String = "",
    var score: Int = 0,
    var currentQuestionOfTheDays : MutableList<CurrentQuestionOfTheDay> = mutableListOf(),
    var pseudo : String = ""
)
