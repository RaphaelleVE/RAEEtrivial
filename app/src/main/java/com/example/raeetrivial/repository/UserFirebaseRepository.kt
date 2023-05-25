package com.example.raeetrivial.repository

import com.example.raeetrivial.domain.CurrentQuestionOfTheDay
import com.example.raeetrivial.domain.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserFirebaseRepository @Inject constructor(private val authRepository : AuthRepository , private val firestore: FirebaseFirestore) {

    fun insertUser(id: String, user: UserFirebase): Boolean {
        //le paramètre passé à document détermine son id. On lui passe l'id de firestore),
        // si le document existe il l'écrase, sinon il l'insère
        //si le set user s'est bien passé, ça renvoie le booleen isSuccessiful
             return firestore.collection(_collection).document(id).set(user).isSuccessful
    }

    fun getAll(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).snapshots().map {
            it.toObjects<UserFirebase>()
        }
    }

    suspend fun getCurrentUser(): UserFirebase? {

        val uid = authRepository.currentUser?.uid
        if(uid != null){
            var user = firestore.collection(_collection).document(uid).snapshots().first().toObject<UserFirebase?>()
            if(user != null){

                return user
            }
        }
        return null


    }

    suspend fun increaseCurrentUserScore(score: Int) {
        val user = getCurrentUser()
        if(user != null){
            user.score += score
            updateCurrentUser(user)
        }
    }
    fun updateCurrentUser(user: UserFirebase) : Boolean{
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

    //companion object gère les constantes, équivalent du static
    companion object {
        private const val _collection: String = "USERS"
    }
}