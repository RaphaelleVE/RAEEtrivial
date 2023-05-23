package com.example.raeetrivial.ui.questions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.Answer
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.example.raeetrivial.network.model.Result
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.QuestionsRepository
import com.example.raeetrivial.ui.baseApp.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.URLDecoder
import okio.ByteString.Companion.encodeUtf8

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val authRepository: AuthRepository
    ): ViewModel(){


    private val _questionsUiState = MutableStateFlow<QuestionsUiState?>(null)
    val questionsUiState : StateFlow<QuestionsUiState?>
        get() = _questionsUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsOfTheDay = questionsRepository.getQuestionsOfTheDay()
            if(questionsOfTheDay != null){
                val currentQuestion = questionsOfTheDay.questions[0]

                _questionsUiState.update { QuestionsUiState(questionsOfTheDay, currentQuestion.question.encodeUtf8().utf8(), currentQuestion.answers) }
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

    fun validateAnswers(answer : Answer, context : Context, baseViewModel : BaseViewModel){
        if(answer.isCorrect) baseViewModel.baseUserFlow.value.score += 10
        Toast.makeText(context,baseViewModel.baseUserFlow.value.score.toString(), Toast.LENGTH_SHORT).show()

        //gestion du score
        /*_currentQuestion.update{
            _questions.value.get(index)
        }*/
    }





}