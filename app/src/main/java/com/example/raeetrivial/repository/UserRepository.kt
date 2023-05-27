package com.example.raeetrivial.repository

import com.example.raeetrivial.domain.CurrentQuestionOfTheDay
import com.example.raeetrivial.domain.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authRepository : AuthRepository,
    private val store: FirebaseFirestore,
    private val utils: FirestoreUtils
    ) {

    //insert user in Firestore
    private fun insertUser(id: String, user: User) {
        store.collection(_collection).document(id).set(user)
    }

    fun getAll(): Flow<List<User>> {
        return store.collection(_collection).snapshots().map {
            it.toObjects<User>()
        }
    }

    suspend fun getCurrentUser(): User? {
        val uid = authRepository.currentUser?.uid
        if(uid != null){
            val user = store
                .collection(_collection)
                .document(uid)
                .snapshots().first().toObject<User?>()
            if(user != null){

                return user
            }
        }
        return null
    }

    fun updateCurrentUser(user: User) : Boolean {
        val uid = authRepository.currentUser?.uid
        if(uid != null){
            store.collection(_collection).document(uid).set(user)
            return true
        }
        return false
    }

    //create the user's current question in firestore when first connect each day
    suspend fun createCurrentQuestionOfTheDay(questionsId: String) {
        val user = getCurrentUser()
        if(user != null){
            val currentQuestionOfTheDay = user.currentQuestionOfTheDays.filter {
                    currentQuestionOfTheDay -> currentQuestionOfTheDay.date == questionsId
                }
            if(currentQuestionOfTheDay.isEmpty()){
                user.currentQuestionOfTheDays
                    .add(CurrentQuestionOfTheDay(questionsId,0))
                updateCurrentUser(user)
            }
        }
    }

    //fetch current question for the user in firestore
    fun getCurrentQuestionOfTheDay(user: User?) : Int? {
        if (user != null) {
            return user.currentQuestionOfTheDays.find{
                    currentQuestionOfTheDay ->
                currentQuestionOfTheDay.date == utils.getQuestionsId()}?.currentQuestion
        }
        return null
    }

    //increment user's current question
    fun incrementCurrentQuestionOfTheDay(user: User?) {
        if (user != null) {
            val currentQuestionOfTheDay = user.currentQuestionOfTheDays.find{
                    currentQuestionOfTheDay ->
                currentQuestionOfTheDay.date == utils.getQuestionsId()
            }
            if( currentQuestionOfTheDay != null){
                currentQuestionOfTheDay.currentQuestion ++
            }
            updateCurrentUser(user)
        }
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return authRepository.signIn(email,password)
    }

    //insert user in firebaseAuth, if success insert user in user's firestore
    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        val user = authRepository.registerUser(email,password)
        if(user != null){
            insertUser(user.uid, User(email, 0, mutableListOf(),email))
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