package com.example.raeetrivial.repository

import java.time.LocalDate
import javax.inject.Inject

class FirestoreUtils @Inject constructor(){

    //get the id for questionsOfTheDay & for user's current question
    fun getQuestionsId(): String {
        val currentDate = LocalDate.now()
        return currentDate.toString()
    }
}