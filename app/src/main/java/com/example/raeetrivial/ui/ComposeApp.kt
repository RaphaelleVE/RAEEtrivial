package com.example.raeetrivial.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raeetrivial.login.LoginScreen
import com.example.raeetrivial.signup.SignUpScreen
import com.example.raeetrivial.ranking.RankingScreen

@Composable fun ComposeApp () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.LOGIN
    ) {
        composable(Route.LOGIN){
            LoginScreen(navController = navController)
        }
        composable(Route.SIGNUP){
            SignUpScreen(navController = navController)
        }
        composable(Route.RANKING){
            RankingScreen(navController = navController)
        }
    }
}

object Route {
    const val LOGIN = "login"
    const val SIGNUP = "sign_up"
    const val RANKING = "ranking"
}

object Argument {

}