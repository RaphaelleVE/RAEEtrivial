package com.example.raeetrivial.ui.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.example.raeetrivial.network.model.Result
import com.example.raeetrivial.repository.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val questionsRepository: QuestionsRepository):
    ViewModel(){

    private val _currentQuestion = MutableStateFlow<Result?>(null)

    val currentQuestion: StateFlow<Result?>
        get() = _currentQuestion

    private val _questions = flow {
        val questions = questionsRepository.getQuestionOfTheDay()
        emit(questions)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    //Lazily = se lance dès que quelqu'un fait l'appel
    //viewModelScope = thread dans lequel se trouve les vues

    val questions : StateFlow<List<Result>>
        get() = _questions

    fun validateAnswers(index: Int){
        //gestion du score
        _currentQuestion.update{
            _questions.value.get(index)
        }

    }



}