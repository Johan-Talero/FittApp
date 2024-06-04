package com.fitness.fittapp.UI.Clases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitness.fittapp.databinding.ActivityClassBinding

class ClassActivity : AppCompatActivity() {

    private lateinit var binding:ActivityClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewToolBar.back.setOnClickListener {
            finish()
        }

    }
}