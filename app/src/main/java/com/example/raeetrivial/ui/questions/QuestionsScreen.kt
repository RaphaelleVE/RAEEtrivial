package com.example.raeetrivial.ui.questions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen() {
    val viewModel = hiltViewModel<QuestionsViewModel>()

    val questions = viewModel.questions.collectAsState().value
    Scaffold() {
        Column(modifier = androidx.compose.ui.Modifier.padding(it)) {
            questions.forEach {
                Text(text = it.question)
            }
        }
    }
}