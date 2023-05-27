package com.example.raeetrivial.repository

import android.text.Html
import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.domain.Question
import com.example.raeetrivial.domain.QuestionsOfTheDay
import com.example.raeetrivial.network.QuestionsOfTheDayApi
import com.google.firebase.firestore.FirebaseFirestore
import com.example.raeetrivial.network.model.Result
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.first

import javax.inject.Inject

class QuestionsRepository @Inject constructor (
    private val store: FirebaseFirestore,
    private val api: QuestionsOfTheDayApi,
    private val userRepository : UserRepository,
    private val utils : FirestoreUtils

){
    suspend fun getQuestionsOfTheDay(): QuestionsOfTheDay {

        var questionsOfTheDay = store
            .collection(_collection)
            .document(utils.getQuestionsId())
            .snapshots().first().toObject<QuestionsOfTheDay>()

        if(questionsOfTheDay == null){
            val response = api.getQuestions()
            val questions : MutableList<Question> = arrayListOf()
            for(result in response.results){
                questions.add(buildQuestion(result))
            }
            insertQuestionsOfTheDay(QuestionsOfTheDay(questions))
            questionsOfTheDay = QuestionsOfTheDay((questions))
        }
        userRepository.createCurrentQuestionOfTheDay(utils.getQuestionsId())
        return questionsOfTheDay
    }

    //build Question form Api response
    private fun buildQuestion(result: Result): Question{
        val answers = buildPossibleAnswers(result)
        return Question(
            result.category,
            answers,
            result.difficulty,
            Html.fromHtml(result.question).toString(),
            result.type)
    }

    //build Answers from Api response
    private fun buildPossibleAnswers(question: Result) : List<Answer>{
        val answers = mutableListOf<Answer>()
        answers.add(Answer(Html.fromHtml(question.correctAnswer).toString(),true))

        question.incorrectAnswers.forEach{
            answers.add(Answer(Html.fromHtml(it).toString(), false))
        }
        answers.shuffle()
        return answers
    }


    private fun insertQuestionsOfTheDay(questionsOfTheDay : QuestionsOfTheDay) : Boolean{
        return store
            .collection(_collection)
            .document(utils.getQuestionsId())
            .set(questionsOfTheDay).isSuccessful
    }

    companion object {
        private const val _collection: String = "QUESTIONS_OF_THE_DAY"

    }
}