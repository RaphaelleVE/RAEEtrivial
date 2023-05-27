package com.example.raeetrivial.network

import com.example.raeetrivial.network.model.QuestionsOfTheDayModelApi
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsOfTheDayApi {

    @GET("api.php")
    suspend fun getQuestions(@Query("amount") amount: Int = 10):
            QuestionsOfTheDayModelApi
}