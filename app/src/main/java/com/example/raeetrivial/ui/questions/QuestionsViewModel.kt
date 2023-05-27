package com.example.raeetrivial.ui.questions


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.domain.Question
import com.example.raeetrivial.domain.QuestionsOfTheDay
import com.example.raeetrivial.domain.User
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.example.raeetrivial.repository.QuestionsRepository
import com.example.raeetrivial.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val userRepository: UserRepository
    ): ViewModel(){

    private val _currentUserFlow = MutableStateFlow<User?>(null)
    private val currentUserFlow : StateFlow<User?>
        get() = _currentUserFlow

    private val _questionsOfTheDayFlow = MutableStateFlow<QuestionsOfTheDay>(QuestionsOfTheDay())
    private val questionsOfTheDayFlow : StateFlow<QuestionsOfTheDay?>
        get() = _questionsOfTheDayFlow

    private val _currentQuestionFlow = MutableStateFlow<Question>(Question())
    val currentQuestionFlow : StateFlow<Question?>
        get() = _currentQuestionFlow

    private val _answeredFlow = MutableStateFlow<Boolean>(false)
    val answeredFlow : StateFlow<Boolean?>
        get() = _answeredFlow

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

            _questionsOfTheDayFlow.update { questionsOfTheDayRecup }

            val currentUserRecup = userRepository.getCurrentUser()

            if (currentUserRecup != null) {
                _currentUserFlow.update { currentUserRecup }
                if (getCurrentQuestionIndex() > 9) {
                    _currentQuestionFlow.update { finalQuestion }
                } else {
                    _currentQuestionFlow.update { questionsOfTheDayFlow.value!!.questions[getCurrentQuestionIndex()] }
                }
            }
        }
    }

    private fun getCurrentQuestionIndex(): Int {
        return userRepository.getCurrentQuestionOfTheDay(currentUserFlow.value) ?: 0
    }

    fun validateAnswers(answer : Answer){
        _answeredFlow.update{true}

        viewModelScope.launch(Dispatchers.IO) {
            if(answer.isCorrect){
                increaseScore(10)
            }
            delay(500)
            goToNextQuestion()
        }
    }

    private fun increaseScore(score: Int) {
        if(currentUserFlow.value != null) {
                currentUserFlow.value!!.score += score
                userRepository.updateCurrentUser(currentUserFlow.value!!)
        }
    }

    fun goToNextQuestion() {
        _answeredFlow.update { false }
        incrementQuestion()

        if (currentUserFlow.value != null) {
            if (getCurrentQuestionIndex() > 9) {
                _currentQuestionFlow.update { finalQuestion }
            } else {
                _currentQuestionFlow.update { questionsOfTheDayFlow.value!!.questions[getCurrentQuestionIndex()] }
            }
        }
    }

    private fun incrementQuestion() {
        if(currentUserFlow.value != null){
            userRepository.incrementCurrentQuestionOfTheDay(currentUserFlow.value)
        }
    }
}