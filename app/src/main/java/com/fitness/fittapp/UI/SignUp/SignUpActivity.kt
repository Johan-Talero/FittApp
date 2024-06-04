package com.fitness.fittapp.UI.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fitness.fittapp.UI.Login.LogInActivity
import com.fitness.fittapp.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.isLoading.collect {
                    binding.loading.isVisible = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            if (checkEmptySlots()) {
                signUpViewModel.register(
                    email = binding.tieUsername.text.toString(),
                    password = binding.tiePassword.text.toString(),
                    age = binding.tieAge.text.toString(),
                    name = binding.tieName.text.toString()
                ) { navigateToLogin() }
            } else {
                Toast.makeText(this, "Debe de llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkEmptySlots(): Boolean {
        return !(binding.tieName.text.toString().isEmpty() ||
                binding.tieAge.text.toString().isEmpty() ||
                binding.tieUsername.toString().isEmpty() ||
                binding.tiePassword.toString().isEmpty())
    }

    private fun navigateToLogin() {
        Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}