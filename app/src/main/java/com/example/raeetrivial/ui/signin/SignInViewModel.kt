package com.example.raeetrivial.ui.signin

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
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _isConnectedFlow = MutableStateFlow<Boolean>(false)
    val isConnectedFlow: StateFlow<Boolean>
        get() = _isConnectedFlow

    private val _isTryConnectionFlow = MutableStateFlow<Boolean>(false)
    val isTryConnectionFlow: StateFlow<Boolean>
        get() = _isTryConnectionFlow

    fun signInUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loggedUser = userRepository.signIn(email, password)
            if (loggedUser != null) {
                _isConnectedFlow.update{ true }
            }
            _isTryConnectionFlow.update{ true }
        }
    }

    fun resetIsTryConnection() {
        _isTryConnectionFlow.update { false }
    }
}