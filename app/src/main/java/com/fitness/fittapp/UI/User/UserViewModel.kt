package com.fitness.fittapp.UI.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitness.fittapp.Data.network.FirebaseDatabaseService
import com.fitness.fittapp.Domain.Model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val firestore: FirebaseDatabaseService) :
    ViewModel() {

    suspend fun getUserById(userId: String): User? {
        return firestore.getUserById(userId)
    }

    fun updateUser(user: User) {
        firestore.updateUser(user)
    }


}