package com.example.raeetrivial.domain

import androidx.compose.ui.graphics.Color
import com.example.raeetrivial.ui.theme.YellowWhite

data class Answer (
    val text : String,
    val isCorrect : Boolean,
    var buttonColor : Color = YellowWhite
        )