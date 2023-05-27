package com.example.raeetrivial.ui.baseApp

import androidx.lifecycle.ViewModel
import com.example.raeetrivial.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){

    fun signOut(){
        return userRepository.signOut()
    }
}