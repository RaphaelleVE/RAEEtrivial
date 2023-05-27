package com.example.raeetrivial.ui.baseApp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.example.raeetrivial.ui.theme.YellowWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(navController: NavController) {
    val viewModel = hiltViewModel<BaseViewModel>()

    val navBarController = rememberNavController()
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(text = "Trivial Pursuit")},
                colors= TopAppBarDefaults.smallTopAppBarColors(containerColor = YellowWhite),
                actions =
                {
                    IconButton(onClick = {
                        viewModel.signOut()
                        navController.navigate(Route.SIGN_UP)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_logout),
                            contentDescription = stringResource(R.string.logout)
                        )
                    }
                },
            )
        },
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
                    label = {
                        Text(
                            color = Color.Black,
                            text = stringResource(R.string.ranking),
                            modifier = Modifier.offset(0.dp, 10.dp))
                    },
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
                    label = {
                        Text(
                            color = Color.Black,
                            text = stringResource(R.string.game),
                            modifier = Modifier.offset(0.dp, 10.dp))
                    },
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
                    label = {
                        Text(
                            color = Color.Black,
                            text = stringResource(R.string.profile),
                            modifier = Modifier.offset(0.dp, 10.dp))
                    },
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        navBarController.navigate(Route.PROFILE)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navBarController,
                startDestination = Route.QUESTIONS
            ) {
                composable(Route.QUESTIONS) {
                    QuestionsScreen()
                }
                composable(Route.RANKING) {
                    RankingScreen()
                }
                composable(Route.PROFILE) {
                    ProfileScreen()
                }
            }
        }
    }
}


