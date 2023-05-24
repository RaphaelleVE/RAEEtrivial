package com.example.raeetrivial.ui.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.repository.AuthRepository
import com.example.raeetrivial.repository.UserFirebaseRepository
import com.example.raeetrivial.domain.UserFirebase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseRepository: UserFirebaseRepository
): ViewModel(){
    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _signupFlow = MutableStateFlow<SignUpUiState>(SignUpUiState(registerSuccessfull = false, triedRegister = false))
    val signupFlow: StateFlow<SignUpUiState> = _signupFlow

    fun signupUser(email:String, password:String){
        //lance un thread, càd une coroutine
        viewModelScope.launch(Dispatchers.IO) {
           val uid = registerUser(email, password)
            if(uid != null) {
                registerUserinFirebase(uid, email)
                _signupFlow.value.registerSuccessfull = true
            }
            _signupFlow.value.triedRegister = true
        }
    }

    suspend fun registerUser(email : String, password : String) : String?{
        return authRepository.signup(email, password)?.uid
    }

    suspend fun registerUserinFirebase(uid : String, email: String){
        firebaseRepository.insertUser(uid, UserFirebase(email,0,0))
    }

}