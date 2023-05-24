package com.example.raeetrivial.domain

data class UserFirebase (
    var email: String = "",
    var score: Int = 0,
    var currentQuestion : Int = 0,
)