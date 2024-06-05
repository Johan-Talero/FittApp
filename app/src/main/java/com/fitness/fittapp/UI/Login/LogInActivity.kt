package com.fitness.fittapp.UI.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.fitness.fittapp.Domain.Model.NotificationWorker
import com.fitness.fittapp.UI.Home.HomeScreenActivity
import com.fitness.fittapp.UI.SignUp.SignUpActivity
import com.fitness.fittapp.databinding.ActivityLogInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val logInViewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPeriodicWork()
        initUI()
    }

    private fun setupPeriodicWork() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(30, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                logInViewModel.isLoading.collect {
                    binding.loading.isVisible = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            if (checkEmptySlots()) {
                logInViewModel.login(
                    user = binding.tieUsername.text.toString(),
                    password = binding.tiePassword.text.toString()
                )
                lifecycleScope.launch {
                    navigateToHome()
                }
            } else {
                Toast.makeText(
                    this,
                    "Debe de llenar los campos de usuario y contraseña",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private suspend fun navigateToHome() {

        logInViewModel.loginResult.collect { userId ->
            if (userId != null) {
                val intent = Intent(this, HomeScreenActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        }

    }

    private fun navigateToRegister() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun checkEmptySlots(): Boolean {
        return !(binding.tieUsername.text.toString().isEmpty() ||
                binding.tiePassword.text.toString().isEmpty()
                )
    }
}