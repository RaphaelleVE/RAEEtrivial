package com.example.raeetrivial.repository

import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.domain.Question
import com.example.raeetrivial.domain.QuestionsOfTheDay
import com.example.raeetrivial.network.QuestionsOfTheDayApi
import com.google.firebase.firestore.FirebaseFirestore
import com.example.raeetrivial.network.model.Result
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.first
import java.net.URLDecoder
import java.time.LocalDate

import javax.inject.Inject

class QuestionsRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val api: QuestionsOfTheDayApi

){


        suspend fun initQuestions(){
            val response = api.getQuestions()
            val questions : MutableList<Question> = arrayListOf()
            for(result in response.results){
                questions.add(buildQuestion(result))
            }
            val questionsOfTheDay=QuestionsOfTheDay(questions)
            insertQuestionsOfTheDay(questionsOfTheDay)
        }

        suspend fun getQuestionsOfTheDay(): QuestionsOfTheDay? {

            val questionsOfTheDay = firestore.collection(_collection).document(getQuestionsId()).snapshots().first().toObject<QuestionsOfTheDay>()

            if(questionsOfTheDay == null){
                val response = api.getQuestions()
                val questions : MutableList<Question> = arrayListOf()
                for(result in response.results){
                    questions.add(buildQuestion(result))
                }
                insertQuestionsOfTheDay(QuestionsOfTheDay(questions))
            }

            return questionsOfTheDay
        }


        /*fun getQuestionsFromDb(): Flow<List<Question>> {
            return firestore.collection(_collection).document(today.toString()).collection(
                _subCollection).snapshots().map { it.toObjects<Question>()}
        }*/

        fun buildQuestion(result: Result): Question{
            val answers = createPossibleAnswers(result)
            return Question(result.category, answers, result.difficulty, URLDecoder.decode(result.question, "UTF-8"), result.type)
        }

        private fun createPossibleAnswers(question: Result) : List<Answer>{
            val answers = mutableListOf<Answer>()
            answers.add(Answer(URLDecoder.decode(question.correctAnswer, "UTF-8"),true))

            question.incorrectAnswers.forEach{
                answers.add(Answer(URLDecoder.decode(it, "UTF-8"), false))
            }
            answers.shuffle()
            return answers
        }


    fun insertQuestionsOfTheDay(questionsOfTheDay : QuestionsOfTheDay) : Boolean{
        return firestore.collection(_collection).document(getQuestionsId()).set(questionsOfTheDay).isSuccessful
    }


    private fun getQuestionsId(): String {
        val currentDate = LocalDate.now()
        return currentDate.toString()
    }

    companion object{
        private const val _collection: String = "QUESTIONS_OF_THE_DAY"


}