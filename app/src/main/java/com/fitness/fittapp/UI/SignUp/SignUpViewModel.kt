package com.fitness.fittapp.UI.SignUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.fittapp.Data.network.AuthService
import com.fitness.fittapp.Data.network.FirebaseDatabaseService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestore: FirebaseDatabaseService
) : ViewModel() {

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun register(
        email: String,
        password: String,
        age: String,
        name: String,
        navigateToLogin: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                authService.register(email, password)
            }

            if (result != null) {
                withContext(Dispatchers.IO) {
                    firestore.addUserToFirestore(
                        uid = result.uid,
                        email = email,
                        password = password,
                        age = age,
                        name = name
                    )
                }
                navigateToLogin()
            } else {
                Log.i("Error DB User", "Ha habido un problema al registrar el usuario")
            }

            _isLoading.value = false
        }
    }

}