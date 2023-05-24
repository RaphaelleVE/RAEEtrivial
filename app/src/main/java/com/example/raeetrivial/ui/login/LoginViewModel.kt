package com.example.raeetrivial.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseRepository: UserFirebaseRepository
): ViewModel() {

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _loginFlow = MutableStateFlow<Boolean>(false)
    val loginFlow: StateFlow<Boolean?> = _loginFlow

    fun loginUser(email: String, password: String) {
        //lance un thread, càd une coroutine
        viewModelScope.launch {
            val isLogged = authRepository.login(email, password)
            if (isLogged != null) {
                _loginFlow.value = true
            }
        }
    }
}