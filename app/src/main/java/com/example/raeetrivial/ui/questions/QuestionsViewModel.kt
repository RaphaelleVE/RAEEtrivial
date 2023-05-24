package com.example.raeetrivial.ui.questions


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.Answer
import com.example.raeetrivial.domain.UserFirebase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.example.raeetrivial.repository.QuestionsRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.ByteString.Companion.encodeUtf8

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val userRepository: UserFirebaseRepository
    ): ViewModel(){


    private val _questionsUiState = MutableStateFlow<QuestionsUiState?>(null)
    val questionsUiState : StateFlow<QuestionsUiState?>
        get() = _questionsUiState

    private val _currentUser = MutableStateFlow<UserFirebase?>(null)
    val currentUser : StateFlow<UserFirebase?>
        get() = _currentUser


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsOfTheDay = questionsRepository.getQuestionsOfTheDay()
            if(questionsOfTheDay != null){
                val currentQuestion = questionsOfTheDay.questions[0]

                _questionsUiState.update { QuestionsUiState(questionsOfTheDay, currentQuestion.question.encodeUtf8().utf8(), currentQuestion.answers, false) }
                val currentUser = userRepository.getCurrentUser()
                if(currentUser != null){
                    _currentUser.update { userRepository.getCurrentUser() }
                }
            }

            //TODO remove log
            Log.d("questionUiState",_questionsUiState.value.toString())

        }
    }

    /*private val _questions = flow {
        val questions = questionsRepository.getQuestionOfTheDay()
        _currentQuestion.value = questions.first();

        emit(questions)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())*/
    //Lazily = se lance dès que quelqu'un fait l'appel
    //viewModelScope = thread dans lequel se trouve les vues

    /*val questions : StateFlow<List<Result>>
        get() = _questions*/

    fun validateAnswers(answer : Answer){
        _questionsUiState.update{QuestionsUiState(questionsUiState.value!!.questions, questionsUiState.value!!.currentQuestion,questionsUiState.value!!.answers, true)}


        viewModelScope.launch {
            if(answer.isCorrect){
                userRepository.increaseCurrentUserScore(10)
                currentUser.value!!.score +=10
            }

            delay(1000)

            goToNextQuestion()
        }

        //gestion du score
        /*_currentQuestion.update{
            _questions.value.get(index)
        }*/
    }

    fun goToNextQuestion() {
        if(questionsUiState.value != null){
            val currentQuestion = questionsUiState.value!!.questions.questions.get(1)
            //.get()..questions[1];
            if (currentQuestion != null) {
                _questionsUiState.update{QuestionsUiState(questionsUiState.value!!.questions, currentQuestion.question, currentQuestion.answers, false)}
            }
        }
    }


}