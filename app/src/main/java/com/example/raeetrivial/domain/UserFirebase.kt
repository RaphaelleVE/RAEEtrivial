package com.example.raeetrivial.domain

data class UserFirebase (
    var email: String = "",
    var score: Int = 0,
    var currentQuestionOfTheDays : MutableList<CurrentQuestionOfTheDay> = mutableListOf(),
    var pseudo : String = ""
)