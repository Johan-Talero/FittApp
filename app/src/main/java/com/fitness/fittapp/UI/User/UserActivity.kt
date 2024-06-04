package com.fitness.fittapp.UI.User

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fitness.fittapp.Domain.Model.User
import com.fitness.fittapp.databinding.ActivityUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getString("userId")
        initUI(user.toString())
    }

    private fun initUI(user: String) {
        initListeners(user)
        printInfo(user)
    }

    private fun printInfo(user: String) {
        lifecycleScope.launch {
            val userData = userViewModel.getUserById(user)
            binding.tieName.setText(userData?.name)
            binding.tieAge.setText(userData?.age)
        }
    }

    private fun initListeners(user:String) {
        binding.viewToolBar.back.setOnClickListener {
            finish()
        }
        binding.btnUpdate.setOnClickListener {
            if(checkEmptySlots()){
                lifecycleScope.launch {
                    val userData = userViewModel.getUserById(user)
                    userData?.name = binding.tieName.text.toString()
                    userData?.age = binding.tieAge.text.toString()
                    if(userData!=null && userData.id != ""){
                        userViewModel.updateUser(userData)
                        finish()
                    }else{
                        Log.i("Update Error","Error al extraer datos del usuario para actualizar")
                    }
                }
                Toast.makeText(this, "Datos de Usuario Actualizados", Toast.LENGTH_SHORT)
                    .show()
            }else {
                Toast.makeText(this, "Por favor rellene los campos necesarios", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkEmptySlots(): Boolean {
        return !(binding.tieAge.text.toString().isEmpty() || binding.tieName.text.toString()
            .isEmpty())
    }
}