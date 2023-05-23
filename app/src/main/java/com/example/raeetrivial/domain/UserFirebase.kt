package com.example.raeetrivial.domain

data class UserFirebase (
    var email: String = "",
    var score: Int = 0,
    var lastQuestion : Int = 0
)