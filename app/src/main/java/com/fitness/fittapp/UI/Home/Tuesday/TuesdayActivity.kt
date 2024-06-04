package com.fitness.fittapp.UI.Home.Tuesday

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fitness.fittapp.R
import com.fitness.fittapp.databinding.ActivityTuesdayBinding

class TuesdayActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTuesdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTuesdayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageURL.setOnClickListener {
            val youtubeVideoVaccine = "https://www.youtube.com/watch?v=RWT3S9cAgw4"
            openVideo(youtubeVideoVaccine)
        }
        binding.viewToolBar.back.setOnClickListener {
            finish()
        }
    }

    private fun openVideo(url: String) {
        try {
            val videoUri = url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUri))
            if (videoUri!= null) {
                startActivity(intent)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error, asegurese de tener youtube", Toast.LENGTH_SHORT).show()
        }
    }

}