package com.example.raeetrivial.ui.profile

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.UserFirebase
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: UserFirebaseRepository
): ViewModel(){

    private val _currentUser = MutableStateFlow<UserFirebase>(UserFirebase())
    val currentUser : StateFlow<UserFirebase> = _currentUser

    private val _tempoPseudo = MutableStateFlow<String>("")
    val tempoPseudo : StateFlow<String> = _tempoPseudo


    init{
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = firebaseRepository.getCurrentUser()
            if(currentUser != null){
                _currentUser.update { if(firebaseRepository.getCurrentUser() != null) firebaseRepository.getCurrentUser()!! else UserFirebase() }
            }
        }
    }

    fun changeUserPseudo(pseudo : String){
        viewModelScope.launch(Dispatchers.IO) {
            if(pseudo.isNotEmpty()){
                _currentUser.value.pseudo = pseudo
               if(firebaseRepository.updateCurrentUser(currentUser.value)) {
                   _tempoPseudo.update { pseudo }
               }
            }
        }

    }
}