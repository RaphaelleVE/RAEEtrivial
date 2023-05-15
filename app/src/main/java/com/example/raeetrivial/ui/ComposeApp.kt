package com.example.raeetrivial.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raeetrivial.baseApp.BaseScreen
import com.example.raeetrivial.login.LoginScreen
import com.example.raeetrivial.ui.questions.QuestionsScreen
import com.example.raeetrivial.ui.signup.SignUpScreen
import com.example.raeetrivial.ranking.RankingScreen
import com.example.raeetrivial.ui.Route.GAME

@Composable
fun ComposeApp () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.QUESTIONS
    ) {
        composable(Route.LOGIN){
            LoginScreen(navController = navController)
        }
        composable(Route.SIGNUP){
            SignUpScreen(navController = navController)
        }

        composable(Route.QUESTIONS){
            QuestionsScreen()

        composable(Route.RANKING){
            RankingScreen(navController = navController)

        composable(Route.BASE){
            BaseScreen(navController = navController)
        }
    }
}

object Route {
    const val LOGIN = "login"
    const val SIGNUP = "sign_up"
    const val QUESTIONS = "questions"
    const val RANKING = "ranking"

    const val GAME = "game"
    const val BASE = "base_screen"
}

object Argument {

}