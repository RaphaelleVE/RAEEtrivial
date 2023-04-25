package com.example.raeetrivial.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)
    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    fun signupUser(email:String, password:String){
        //lance un thread, càd une coroutine
        viewModelScope.launch {
            _signupFlow.value = repository.signup(email, password)
        }
    }

}