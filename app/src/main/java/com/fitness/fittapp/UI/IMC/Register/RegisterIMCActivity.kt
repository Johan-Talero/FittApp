package com.fitness.fittapp.UI.IMC.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.fitness.fittapp.R
import com.fitness.fittapp.databinding.ActivityRegisterImcactivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterIMCActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterImcactivityBinding
    private val registerIMCViewModel: RegisterIMCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterImcactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId = intent.extras?.getString("userId")
        initUI(userId.toString())
    }

    private fun initUI(userId: String) {
        initListeners(userId)
    }

    private fun checkEmptySlots(): Boolean {
        return !(binding.tieHeight.text.toString().isEmpty() || binding.tieWeight.text.toString()
            .isEmpty())
    }

    private fun initListeners(userId: String) {
        binding.btnRegisterIMC.setOnClickListener {
            if (checkEmptySlots()) {
                lifecycleScope.launch {
                    registerIMCViewModel.registerNewIMC(
                        userId,
                        binding.tieHeight.text.toString(),
                        binding.tieWeight.text.toString()
                    )
                }
                Toast.makeText(this, "Registro de IMC Exitoso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Debe de llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.viewToolBar.back.setOnClickListener {
            finish()
        }
    }
}