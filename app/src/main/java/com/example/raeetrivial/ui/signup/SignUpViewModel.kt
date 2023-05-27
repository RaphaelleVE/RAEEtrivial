package com.example.raeetrivial.ui.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raeetrivial.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _tryRegisterFlow = MutableStateFlow<Boolean>(false)
    val tryRegisterFlow: StateFlow<Boolean> = _tryRegisterFlow

    private val _succesRegisterFlow = MutableStateFlow<Boolean>(false)
    val succesRegisterFlow: StateFlow<Boolean> = _succesRegisterFlow

    fun signupUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = registerUser(email, password)
            if (uid != null) {
                _succesRegisterFlow.update{true}
            }
            _tryRegisterFlow.update{true}
        }
    }

    private suspend fun registerUser(email: String, password: String) : String?{
        return userRepository.registerUser(email, password)?.uid
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
    fun resetIsTryRegister() {
        _tryRegisterFlow.update { false }
    }
}