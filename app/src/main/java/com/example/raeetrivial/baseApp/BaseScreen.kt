package com.example.raeetrivial.baseApp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.raeetrivial.R
import com.example.raeetrivial.game.GameScreen
import com.example.raeetrivial.login.LoginScreen
import com.example.raeetrivial.signup.SignUpScreen
import com.example.raeetrivial.ui.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(navController: NavController) {
    val navBarController = rememberNavController()
    Scaffold (
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_question_bubble_48px),
                            contentDescription = stringResource(R.string.game)
                        )
                    },
                    label = { Text(text = stringResource(R.string.game)) },
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        navBarController.navigate(Route.GAME)
                    }
                )
            }
        }
    ){it.calculateBottomPadding()
        NavHost(
            navController = navBarController,
            startDestination = Route.GAME
        ) {
            composable(Route.GAME){
                GameScreen(navController = navController)
            }
        }
    }
}


