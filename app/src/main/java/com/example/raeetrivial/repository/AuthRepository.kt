package com.example.raeetrivial.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    suspend fun signIn(email: String, password: String): FirebaseUser?{
        return try{
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }

    fun signOut(){
        firebaseAuth.signOut()
    }
}