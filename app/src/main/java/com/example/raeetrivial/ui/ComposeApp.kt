package com.example.raeetrivial.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raeetrivial.login.LoginScreen

@Composable fun ComposeApp () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.LOGIN
    ) {
        composable(Route.LOGIN){
            LoginScreen(navController = navController)
        }
    }
}

object Route {
    const val LOGIN = "login"
}

object Argument {
    const val USERNAME = "username"
}