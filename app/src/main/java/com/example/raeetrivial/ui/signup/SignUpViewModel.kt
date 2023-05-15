package com.example.raeetrivial.ui.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import com.example.raeetrivial.domain.UserFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseRepository: UserFirebaseRepository
): ViewModel(){

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _signupFlow = MutableStateFlow<Boolean>(false)
    val signupFlow: StateFlow<Boolean> = _signupFlow

    fun signupUser(email:String, password:String){
        //lance un thread, càd une coroutine
        viewModelScope.launch {
            val uid = authRepository.signup(email, password)?.uid
            if(uid != null) {
                _signupFlow.value = firebaseRepository.insertUser(uid, UserFirebase(email))
            }
        }
    }

}