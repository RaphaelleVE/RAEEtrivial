package com.example.raeetrivial.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raeetrivial.ui.baseApp.BaseScreen
import com.example.raeetrivial.ui.login.LoginScreen
import com.example.raeetrivial.ui.signup.SignUpScreen

@Composable
fun ComposeApp () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.BASE
    ) {
        composable(Route.LOGIN){
            LoginScreen(navController = navController)
        }

        composable(Route.SIGNUP){
            SignUpScreen(navController = navController)
        }

        composable(Route.BASE){
            BaseScreen(navController = navController)
        }
    }
}

