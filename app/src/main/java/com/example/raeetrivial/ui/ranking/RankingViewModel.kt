package com.example.raeetrivial.ui.ranking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.UserFirebase
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import com.example.raeetrivial.ui.questions.QuestionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel

class RankingViewModel @Inject constructor(
    private val firebaseRepository: UserFirebaseRepository
): ViewModel(){

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _rankingFLow = MutableStateFlow< Flow<List<UserFirebase>>>(emptyFlow())
    val rankingFlow: StateFlow<Flow<List<UserFirebase>>> = _rankingFLow

    init {
       getUsersFirebase()
    }

    private fun getUsersFirebase(){
        //lance un thread, càd une coroutine
        viewModelScope.launch {
                _rankingFLow.value = firebaseRepository.getAll()
        }
    }

}