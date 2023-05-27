package com.example.raeetrivial.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.User
import com.example.raeetrivial.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel

class RankingViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _rankingFLow = flow {
        emitAll(userRepository.getAll())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val rankingFlow: StateFlow<List<User>> = _rankingFLow
}