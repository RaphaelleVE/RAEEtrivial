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
import java.time.LocalDate

import javax.inject.Inject

class QuestionsRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val api: QuestionsOfTheDayApi,
    private val userRepository : UserFirebaseRepository

){
        suspend fun getQuestionsOfTheDay(): QuestionsOfTheDay? {

            var questionsOfTheDay = firestore.collection(_collection).document(getQuestionsId()).snapshots().first().toObject<QuestionsOfTheDay>()

            if(questionsOfTheDay == null){
                val response = api.getQuestions()
                val questions : MutableList<Question> = arrayListOf()
                for(result in response.results){
                    questions.add(buildQuestion(result))
                }
                insertQuestionsOfTheDay(QuestionsOfTheDay(questions))
                questionsOfTheDay = QuestionsOfTheDay((questions))
            }
            userRepository.createCurrentQuestionOfTheDay(getQuestionsId())
            return questionsOfTheDay
        }


        /*fun getQuestionsFromDb(): Flow<List<Question>> {
            return firestore.collection(_collection).document(today.toString()).collection(
                _subCollection).snapshots().map { it.toObjects<Question>()}
        }*/

        private fun buildQuestion(result: Result): Question{
            val answers = createPossibleAnswers(result)
            return Question(result.category, answers, result.difficulty, Html.fromHtml(result.question).toString(), result.type)
        }

        private fun createPossibleAnswers(question: Result) : List<Answer>{
            val answers = mutableListOf<Answer>()
            answers.add(Answer(Html.fromHtml(question.correctAnswer).toString(),true))

            question.incorrectAnswers.forEach{
                answers.add(Answer(Html.fromHtml(it).toString(), false))
            }
            answers.shuffle()
            return answers
        }


    private fun insertQuestionsOfTheDay(questionsOfTheDay : QuestionsOfTheDay) : Boolean{
        return firestore.collection(_collection).document(getQuestionsId()).set(questionsOfTheDay).isSuccessful
    }


    private fun getQuestionsId(): String {
        val currentDate = LocalDate.now()
        return currentDate.toString()
    }

    companion object {
        private const val _collection: String = "QUESTIONS_OF_THE_DAY"

    }
}