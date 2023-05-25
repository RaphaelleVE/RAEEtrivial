package com.example.raeetrivial.ui.questions


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.domain.Question
import com.example.raeetrivial.domain.QuestionsOfTheDay
import com.example.raeetrivial.domain.UserFirebase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.example.raeetrivial.repository.QuestionsRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val userRepository: UserFirebaseRepository
    ): ViewModel(){

    private val _currentUser = MutableStateFlow<UserFirebase?>(null)
    val currentUser : StateFlow<UserFirebase?>
        get() = _currentUser

    private val _questionsOfTheDay = MutableStateFlow<QuestionsOfTheDay>(QuestionsOfTheDay())
    val questionsOfTheDay : StateFlow<QuestionsOfTheDay?>
        get() = _questionsOfTheDay

    private val _currentQuestion = MutableStateFlow<Question>(Question())
    val currentQuestion : StateFlow<Question?>
        get() = _currentQuestion

    private val _answered = MutableStateFlow<Boolean>(false)
    val answered : StateFlow<Boolean?>
        get() = _answered

    private val finalQuestion = Question(
        "",
        emptyList(),
        "",
        "You have answered all questions for today, come back tomorrow !",
        ""
    )


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsOfTheDayRecup = questionsRepository.getQuestionsOfTheDay()

            _questionsOfTheDay.update { questionsOfTheDayRecup }

            val currentUserRecup = userRepository.getCurrentUser()

            if (currentUserRecup != null) {
                _currentUser.update { currentUserRecup }
                if (getCurrentQuestionIndex() > 9) {
                    _currentQuestion.update { finalQuestion }
                } else {
                    _currentQuestion.update { questionsOfTheDay.value!!.questions[getCurrentQuestionIndex()] }
                }
            }
        }
    }

    private fun getCurrentQuestionIndex(): Int {
        val index = userRepository.getCurrentQuestionOfTheDay(currentUser.value)
        if(index != null){
            return index
        } else {
            return 0
        }
    }

    fun validateAnswers(answer : Answer){
        _answered.update{true}

        viewModelScope.launch(Dispatchers.IO) {
            if(answer.isCorrect){
                increaseScore(10)
            }
            delay(500)
            goToNextQuestion()
        }
    }

    private fun increaseScore(score: Int) {
        if(currentUser.value != null) {
                currentUser.value!!.score += score
                userRepository.updateCurrentUser(currentUser.value!!)
        }
    }

    fun goToNextQuestion() {
        _answered.update { false }
        incrementQuestion()

        if (currentUser.value != null) {
            if (getCurrentQuestionIndex() > 9) {
                _currentQuestion.update { finalQuestion }
            } else {
                _currentQuestion.update { questionsOfTheDay.value!!.questions[getCurrentQuestionIndex()] }
            }
        }
    }

    private fun incrementQuestion() {
        if(currentUser.value != null){
            userRepository.incrementCurrentQuestionOfTheDay(currentUser.value)
        }
    }


}