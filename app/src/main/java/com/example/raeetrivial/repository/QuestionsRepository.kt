package com.example.raeetrivial.repository

import com.example.raeetrivial.network.QuestionsOfTheDayApi
import com.google.firebase.firestore.FirebaseFirestore
import com.example.raeetrivial.network.model.Result
import javax.inject.Inject

class QuestionsRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val api: QuestionsOfTheDayApi

){

    suspend fun getQuestionOfTheDay(): List<Result>{
        val response = api.getQuestions()
        return response.results
    }

}