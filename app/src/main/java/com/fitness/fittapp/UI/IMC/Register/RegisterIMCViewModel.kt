package com.fitness.fittapp.UI.IMC.Register

import androidx.lifecycle.ViewModel
import com.fitness.fittapp.Data.network.FirebaseDatabaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterIMCViewModel @Inject constructor(private val firestore: FirebaseDatabaseService) :
    ViewModel() {

    suspend fun registerNewIMC(userId: String, height: String, weight: String) {
        val heightData = height.toDouble()
        val weightData = weight.toDouble()
        val imc = (weightData / (heightData*heightData))
        firestore.registerNewIMC(imc,userId)
    }
}