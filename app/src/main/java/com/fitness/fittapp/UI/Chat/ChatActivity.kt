package com.fitness.fittapp.UI.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitness.fittapp.R
import com.fitness.fittapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewToolBar.back.setOnClickListener {
            finish()
        }
    }
}