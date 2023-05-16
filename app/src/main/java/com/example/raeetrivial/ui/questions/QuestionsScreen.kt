package com.example.raeetrivial.ui.questions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen() {
    val viewModel = hiltViewModel<QuestionsViewModel>()

    val questionsUiState = viewModel.questionsUiState.collectAsState().value
    Scaffold() {
        Column(modifier = androidx.compose.ui.Modifier.padding(it)) {
            if (questionsUiState != null) {
                Text(text = questionsUiState.currentQuestion)
                questionsUiState.answers.forEach{
                    Button(modifier = Modifier.fillMaxWidth().padding(5.dp).height(60.dp),
                        shape = RoundedCornerShape(7.dp),
                        onClick = {
                            //viewModel.signupUser(email, password)
                        }
                    ) {
                        Text(
                            fontSize = (20.sp),
                            text = it.text,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
            }

        }
    }
}