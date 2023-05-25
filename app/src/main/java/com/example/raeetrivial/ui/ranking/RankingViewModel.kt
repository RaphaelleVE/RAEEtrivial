package com.example.raeetrivial.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.UserFirebase
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel

class RankingViewModel @Inject constructor(
    private val firebaseRepository: UserFirebaseRepository
): ViewModel(){

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _rankingFLow = flow {
        emitAll(firebaseRepository.getAll())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val rankingFlow: StateFlow<List<UserFirebase>> = _rankingFLow

init {
}


 }