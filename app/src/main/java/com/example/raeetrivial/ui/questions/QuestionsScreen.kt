package com.example.raeetrivial.ui.questions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.theme.MainDarkBleue
import com.example.raeetrivial.ui.theme.Pink80
import com.example.raeetrivial.ui.theme.YellowWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen() {
    val viewModel = hiltViewModel<QuestionsViewModel>()

    val questionsUiState = viewModel.questionsUiState.collectAsState().value
        Column(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.loginPadding))) {
            if (questionsUiState != null) {
                Card (
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
                    ),
                content = {
                        Text( modifier = Modifier.padding(20.dp),
                            fontSize = (20.sp),
                            style = MaterialTheme.typography.titleMedium,
                            text = questionsUiState.currentQuestion)
                })
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.miniSpacer))
                )
                questionsUiState.answers.forEach{
                    ExtendedFloatingActionButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .height(dimensionResource(id = R.dimen.bigButtonHeight)),
                        shape = RoundedCornerShape(7.dp),
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.Black,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 1.dp
                        ),
                        onClick = {
                            //viewModel.signupUser(email, password)
                        }
                    ) {
                        Row {
                            Icon(
                                modifier = Modifier.rotate(90F),
                                painter = painterResource (id =
                                    R.drawable.ic_triangle_48px),
                                tint = Pink80,
                                contentDescription = "Icon response"

                            )
                            Text(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(dimensionResource(id = R.dimen.miniSpacer)),
                                fontSize = (20.sp),
                                text = it.text,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.questionSpacer))
                    )
                }

            }

        }

}