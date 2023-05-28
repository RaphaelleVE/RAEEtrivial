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

    private val _connectionSucceededFlow = MutableStateFlow<Boolean>(false)
    val connectionSucceededFlow: StateFlow<Boolean>
        get() = _connectionSucceededFlow

    private val _connectionIntendedFlow = MutableStateFlow<Boolean>(false)
    val connectionIntendedFlow: StateFlow<Boolean>
        get() = _connectionIntendedFlow

    fun signInUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loggedUser = userRepository.signIn(email, password)
            if (loggedUser != null) {
                _connectionSucceededFlow.update{ true }
            }
            _connectionIntendedFlow.update{ true }
        }
    }

    fun resetConnectionIntended() {
        _connectionIntendedFlow.update { false }
    }
}