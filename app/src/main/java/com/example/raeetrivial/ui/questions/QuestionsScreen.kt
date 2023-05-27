package com.example.raeetrivial.ui.questions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.theme.MainDarkBleue
import com.example.raeetrivial.ui.theme.Pink80
import com.example.raeetrivial.ui.theme.YellowWhite

@Composable
fun QuestionsScreen() {
    val viewModel = hiltViewModel<QuestionsViewModel>()
    val currentQuestion = viewModel.currentQuestionFlow.collectAsState().value
    val answered = viewModel.answeredFlow.collectAsState().value

    var selectedIndex by remember { mutableStateOf(-1) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.loginPadding))
    ) {
        if (currentQuestion != null && answered != null) {
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = YellowWhite,
                    contentColor = Color.Black
                ),
                border = BorderStroke(
                    5.dp,
                    MainDarkBleue
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                )
            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    fontSize = (17.sp),
                    style = MaterialTheme.typography.titleMedium,
                    text = currentQuestion.question
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.miniSpacer))
            )
            currentQuestion.answers.forEachIndexed { index, it ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .height(dimensionResource(id = R.dimen.answerButtonHeight)),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        //the condition in containerColor allows to modifiy the color of the clicked
                        // answer according to the rightness of the answer
                        containerColor = if (answered && selectedIndex == index) {
                            if (it.isCorrect){
                                Color.Green
                            } else {
                                Color.Red
                            }
                        } else {
                            YellowWhite
                        },
                        contentColor = Color.Black,
                        disabledContainerColor = YellowWhite,
                        ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 1.dp
                    ),
                    onClick = {
                        selectedIndex = index
                        viewModel.validateAnswers(it)
                    }
                ){
                    Row {
                        Icon(
                            modifier = Modifier.rotate(90F),
                            painter = painterResource(
                                id =
                                R.drawable.ic_triangle_48px
                            ),
                            tint = Pink80,
                            contentDescription = "Icon response"

                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.miniSpacer)),
                            fontSize = (17.sp),
                            text = it.text,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}