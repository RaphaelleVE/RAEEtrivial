package com.example.raeetrivial.ui.baseApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.domain.UserFirebase
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserFirebaseRepository
): ViewModel(){

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _baseUserFlow = MutableStateFlow<UserFirebase>(UserFirebase("",0, mutableListOf()))
    val baseUserFlow: StateFlow<UserFirebase> = _baseUserFlow

    fun logout(){
        return authRepository.logout()
    }
    private val _currentUser = MutableStateFlow<UserFirebase?>(null)
    val currentUser : StateFlow<UserFirebase?> = _currentUser

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = userRepository.getCurrentUser()
            if(currentUser != null){
                _currentUser.update { userRepository.getCurrentUser() }
            }
        }
    }


}