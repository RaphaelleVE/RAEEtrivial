package com.example.raeetrivial.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.User
import com.example.raeetrivial.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){

    private val _currentUserFlow = MutableStateFlow<User>(User())
    val currentUserFlow : StateFlow<User> = _currentUserFlow

    private val _tempoPseudoFlow = MutableStateFlow<String>("")
    val tempoPseudoFlow : StateFlow<String> = _tempoPseudoFlow

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = userRepository.getCurrentUser()
            if(currentUser != null){
                _currentUserFlow.update {
                    if(userRepository.getCurrentUser() != null) userRepository.getCurrentUser()!!
                    else User()
                }
            }
        }
    }

    fun changeUserPseudo(pseudo : String){
        viewModelScope.launch(Dispatchers.IO) {
            if(pseudo.isNotEmpty()){
                _currentUserFlow.value.pseudo = pseudo
               if(userRepository.updateCurrentUser(currentUserFlow.value)) {
                   _tempoPseudoFlow.update { pseudo }
               }
            }
        }
    }
}