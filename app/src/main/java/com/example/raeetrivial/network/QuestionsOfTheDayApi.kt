package com.example.raeetrivial.network

import com.example.raeetrivial.network.model.QuestionsOfTheDayModelApi
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsOfTheDayApi {

    @GET("api.php")
    suspend fun getQuestions(@Query("amount") amount: Int = 10):
    //Pour créer cette classe, création d'une classe à l'aide du plugin JSON blabla qui
    // permet de mapper un objet à un format de JSON (muy pratique), dans les paramètres advanced, ajouter MOSHI reflect pour le mappage)
            QuestionsOfTheDayModelApi
}