package com.fitness.fittapp.Data.network

import com.fitness.fittapp.Data.response.ImcResponse
import com.fitness.fittapp.Data.response.UserResponse
import com.fitness.fittapp.Domain.Imc
import com.fitness.fittapp.Domain.Model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirebaseDatabaseService @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        const val USERS_PATH = "Users"
        const val IMC_PATH = "Imc"
    }

    suspend fun getAllImcs(userId: String): List<Imc> {
        return firestore.collection(IMC_PATH).get().await()
            .map {
                val imcResponse = it.toObject(ImcResponse::class.java)
                if (imcResponse.userId == userId) {
                    imcResponse.toDomain()
                } else {
                    null
                }
            }
            .filterNotNull()
    }

    suspend fun addUserToFirestore(
        uid: String,
        email: String,
        password: String,
        age: String,
        name: String
    ) {
        val userMap = mapOf(
            "uid" to uid,
            "email" to email,
            "password" to password,
            "age" to age,
            "name" to name
        )
        firestore.collection(USERS_PATH).document(uid).set(userMap).await()
    }

    private fun generateId(): String {
        return Date().time.toString()
    }

    suspend fun registerNewIMC(imc: Double, userId: String) {
        val id = generateId()
        val imcMap = mapOf(
            "userId" to userId,
            "imcData" to imc.toString()
        )
        firestore.collection(IMC_PATH).document(id).set(imcMap).await()
    }

    suspend fun getUserById(userId: String): User? {
        return firestore.collection(USERS_PATH).document(userId).get().await()
            .toObject(UserResponse::class.java)?.toDomain()
    }

    fun updateUser(user: User) {
        val userMap = mapOf(
            "uid" to user.id,
            "email" to user.email,
            "password" to user.password,
            "age" to user.age,
            "name" to user.name
        )
        firestore.collection(USERS_PATH).document(user.id).set(userMap)
            .addOnSuccessListener {
                println("Usuario actualizado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar el usuario: $e")
            }
    }

}