package com.fitness.fittapp.UI.Home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.fitness.fittapp.R
import com.fitness.fittapp.UI.Chat.ChatActivity
import com.fitness.fittapp.UI.Clases.ClassActivity
import com.fitness.fittapp.UI.Home.Friday.FridayActivity
import com.fitness.fittapp.UI.Home.Monday.MondayActivity
import com.fitness.fittapp.UI.Home.Saturday.SaturdayActivity
import com.fitness.fittapp.UI.Home.Thursday.ThursdayActivity
import com.fitness.fittapp.UI.Home.Tuesday.TuesdayActivity
import com.fitness.fittapp.UI.Home.Wednesday.WednesdayActivity
import com.fitness.fittapp.UI.IMC.ConsultIMCActivity
import com.fitness.fittapp.UI.User.UserActivity
import com.fitness.fittapp.databinding.ActivityHomeScreenBinding
import com.google.android.material.navigation.NavigationView

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId = intent.extras?.getString("userId")

        initUI(userId.toString())
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initUI(userId: String) {
        initListeners()
        initMenu(userId)
    }

    private fun initListeners() {
        binding.monday.setOnClickListener {
            startActivity(Intent(this, MondayActivity::class.java))
        }
        binding.tuesday.setOnClickListener {
            startActivity(Intent(this, TuesdayActivity::class.java))
        }
        binding.wednesday.setOnClickListener {
            startActivity(Intent(this, WednesdayActivity::class.java))
        }
        binding.thursday.setOnClickListener {
            startActivity(Intent(this, ThursdayActivity::class.java))
        }
        binding.friday.setOnClickListener {
            startActivity(Intent(this, FridayActivity::class.java))
        }
        binding.saturday.setOnClickListener {
            startActivity(Intent(this, SaturdayActivity::class.java))
        }
        binding.navigationView
    }

    private fun initMenu(userId: String) {
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.navigation_view)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.profile -> {
                    val intent = Intent(this, UserActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    true
                }

                R.id.imc -> {
                    val intent = Intent(this, ConsultIMCActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    true
                }

                R.id.collection -> {
                    startActivity(Intent(this, ChatActivity::class.java))
                    true
                }

                R.id.classes -> {
                    startActivity(Intent(this, ClassActivity::class.java))
                    true
                }

                R.id.logOut -> {
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}