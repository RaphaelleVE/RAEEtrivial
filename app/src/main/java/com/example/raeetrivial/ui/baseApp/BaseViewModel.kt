package com.example.raeetrivial.ui.baseApp

import androidx.lifecycle.ViewModel
import com.example.raeetrivial.domain.UserFirebase
import com.example.raeetrivial.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel(){

    //MutableStateFlow : l'ui ne vient pas modifier la data du viewModel. On a donc besoin
    //d'une variable dont on peut changer la valeur
    //c'est un bus auquel la viewModel est abonnée
    private val _baseUserFlow = MutableStateFlow<UserFirebase>(UserFirebase("test",0,0))
    val baseUserFlow: StateFlow<UserFirebase> = _baseUserFlow

    fun logout(){
        return authRepository.logout()
    }


}