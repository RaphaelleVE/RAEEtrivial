package com.example.raeetrivial.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.UserFirebase
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: UserFirebaseRepository
): ViewModel(){

    private val _currentUser = MutableStateFlow<UserFirebase?>(null)
    val currentUser : StateFlow<UserFirebase?> = _currentUser

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = firebaseRepository.getCurrentUser()
            if(currentUser != null){
                _currentUser.update { firebaseRepository.getCurrentUser() }
            }
        }
    }
}