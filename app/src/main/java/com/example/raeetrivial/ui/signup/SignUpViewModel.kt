package com.example.raeetrivial.ui.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import com.example.raeetrivial.domain.UserFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseRepository: UserFirebaseRepository
): ViewModel() {
    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _signupFlow = MutableStateFlow<SignUpUiState>(
        SignUpUiState(
            registerSuccessfull = false,
            triedRegister = false
        )
    )
    val signupFlow: StateFlow<SignUpUiState> = _signupFlow

    private val _tryRegisterFlow = MutableStateFlow<Boolean>(false)
    val tryRegisterFlow: StateFlow<Boolean> = _tryRegisterFlow

    private val _succesRegisterFlow = MutableStateFlow<Boolean>(false)
    val succesRegisterFlow: StateFlow<Boolean> = _succesRegisterFlow

    fun signupUser(email: String, password: String) {
        //lance un thread, càd une coroutine
        viewModelScope.launch(Dispatchers.IO) {
            val uid = registerUser(email, password)
            if (uid != null) {
                registerUserinFirebase(uid, email)
                _succesRegisterFlow.value = true
            }
            _tryRegisterFlow.value = true
        }
    }

    suspend fun registerUser(email: String, password: String): String? {
        return authRepository.signup(email, password)?.uid
    }

    suspend fun registerUserinFirebase(uid: String, email: String) {
        firebaseRepository.insertUser(uid, UserFirebase(email, 0, mutableListOf(),email))
    }

    fun confirmationPasswordCheck(
        password: String,
        confirmationPassword: String
    ): Boolean {
        if (password == confirmationPassword) {
            return true
        }
        return false
    }
}