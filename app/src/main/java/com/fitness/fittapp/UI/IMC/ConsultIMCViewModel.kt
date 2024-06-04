package com.fitness.fittapp.UI.IMC

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.fittapp.Data.network.FirebaseDatabaseService
import com.fitness.fittapp.Domain.Imc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ConsultIMCViewModel @Inject constructor(private val repository: FirebaseDatabaseService) : ViewModel() {

    suspend fun getIMCList(userId:String): List<Imc> {
        return repository.getAllImcs(userId)
    }

}
