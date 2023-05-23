package com.example.raeetrivial.ui.baseApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.ranking.RankingScreen
import com.example.raeetrivial.ui.Route
import com.example.raeetrivial.ui.profile.ProfileScreen
import com.example.raeetrivial.ui.questions.QuestionsScreen
import com.example.raeetrivial.ui.questions.QuestionsViewModel
import com.example.raeetrivial.ui.theme.BlueWhite
import com.example.raeetrivial.ui.theme.MainDarkBleue
import com.example.raeetrivial.ui.theme.MainYellow
import com.example.raeetrivial.ui.theme.YellowWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(navController: NavController) {
    val viewModel = hiltViewModel<BaseViewModel>()

    val navBarController = rememberNavController()
    Scaffold (
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            NavigationBar (containerColor = YellowWhite,
                ){
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_ranking_48px),
                            tint = Color.Black,
                            contentDescription = stringResource(R.string.ranking)
                        )
                    },
                    label = { Text(color = Color.Black,
                        text = stringResource(R.string.ranking)) },
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        navBarController.navigate(Route.RANKING)
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_question_bubble_48px),
                            tint = Color.Black,
                            contentDescription = stringResource(R.string.game)
                        )
                    },
                    label = { Text(color = Color.Black,text = stringResource(R.string.game)) },
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        navBarController.navigate(Route.QUESTIONS)
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_profile_48px),
                            tint = Color.Black,
                            contentDescription = stringResource(R.string.profile)
                        )
                    },
                    label = { Text(color = Color.Black,text = stringResource(R.string.profile)) },
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        navBarController.navigate(Route.PROFILE)
                    }
                )
            }
        }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navBarController,
                startDestination = Route.QUESTIONS
            ) {
                composable(Route.QUESTIONS) {
                    QuestionsScreen(baseViewModel = viewModel)
                }
                composable(Route.RANKING) {
                    RankingScreen(navController = navBarController)
                }
                composable(Route.PROFILE) {
                    ProfileScreen()
                }
            }
        }
    }
}


