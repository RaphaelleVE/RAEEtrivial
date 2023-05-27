package com.example.raeetrivial.repository

import com.example.raeetrivial.domain.CurrentQuestionOfTheDay
import com.example.raeetrivial.domain.UserFirebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class UserRepository @Inject constructor(private val authRepository : AuthRepository, private val firestore: FirebaseFirestore) {

    private fun insertUser(id: String, user: UserFirebase) {
        firestore.collection(_collection).document(id).set(user)
    }

    fun getAll(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).snapshots().map {
            it.toObjects<UserFirebase>()
        }
    }

    suspend fun getCurrentUser(): UserFirebase? {

        val uid = authRepository.currentUser?.uid
        if(uid != null){
            val user = firestore.collection(_collection).document(uid).snapshots().first().toObject<UserFirebase?>()
            if(user != null){

                return user
            }
        }
        return null


    }
    fun updateCurrentUser(user: UserFirebase) : Boolean {
        val uid = authRepository.currentUser?.uid
        if(uid != null){
            firestore.collection(_collection).document(uid).set(user)
            return true
        }
        return false
    }

    suspend fun createCurrentQuestionOfTheDay(questionsId: String) {
        val user = getCurrentUser()
        if(user != null){
            val currentQuestionOfTheDay = user.currentQuestionOfTheDays.filter {
                    currentQuestionOfTheDay -> currentQuestionOfTheDay.date == questionsId
                }
            if(currentQuestionOfTheDay.isEmpty()){
                user.currentQuestionOfTheDays.add(CurrentQuestionOfTheDay(questionsId,0))
                updateCurrentUser(user)
            }
        }
    }

    fun getCurrentQuestionOfTheDay(user: UserFirebase?) : Int? {
        if (user != null) {
            return user.currentQuestionOfTheDays.find{ currentQuestionOfTheDay -> currentQuestionOfTheDay.date == getQuestionsId()}?.currentQuestion
        }
        return null
    }
    fun incrementCurrentQuestionOfTheDay(user: UserFirebase?) {
        if (user != null) {
            val currentQuestionOfTheDay = user.currentQuestionOfTheDays.find{ currentQuestionOfTheDay -> currentQuestionOfTheDay.date == getQuestionsId()}
            if( currentQuestionOfTheDay != null){
                currentQuestionOfTheDay.currentQuestion ++
            }
            updateCurrentUser(user)
        }
    }

    private fun getQuestionsId(): String {
        val currentDate = LocalDate.now()
        return currentDate.toString()
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return authRepository.signIn(email,password)
    }

    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        val user = authRepository.registerUser(email,password)
        if(user != null){
            insertUser(user.uid, UserFirebase(email, 0, mutableListOf(),email))
            return user
        }
        return null
    }

    fun signOut() {
        return authRepository.signOut()
    }

    companion object {
        private const val _collection: String = "USERS"
    }
}