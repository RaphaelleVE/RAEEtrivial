package com.example.raeetrivial.repository

import com.example.raeetrivial.domain.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun insertUser(id: String, user: UserFirebase): Boolean {
        //le paramètre passé à document détermine son id. On lui passe l'id de firestore),
        // si le document existe il l'écrase, sinon il l'insère
        //si le set user s'est bien passé, ça renvoie le booleen isSuccessiful
        return firestore.collection(_collection).document(id).set(user).isSuccessful
    }

    fun getAll(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).snapshots().map { it.toObjects<UserFirebase>() }
    }

    fun getUser(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).snapshots().map {
            it.toObjects<UserFirebase>() }
    }

    //companion object gère les constantes, équivalent du static
    companion object {
        private const val _collection: String = "USERS"
    }
}