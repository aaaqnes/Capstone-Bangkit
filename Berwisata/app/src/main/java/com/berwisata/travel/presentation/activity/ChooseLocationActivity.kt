package com.berwisata.travel.presentation.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berwisata.travel.R
import com.berwisata.travel.databinding.ActivityChooseLocationBinding
import com.google.firebase.auth.FirebaseAuth

class ChooseLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLocationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val sharedPref = getSharedPreferences("locPref", Context.MODE_PRIVATE)

        binding.btnPusat.setOnClickListener {
            with(sharedPref.edit()) {
                putString("location", "jakpus")
                apply()
            }
            checkUserLoginStatus()
        }

        binding.btnBarat.setOnClickListener {
            with(sharedPref.edit()) {
                putString("location", "jakbar")
                apply()
            }
            checkUserLoginStatus()
        }

        binding.btnSelatan.setOnClickListener {
            with(sharedPref.edit()) {
                putString("location", "jaksel")
                apply()
            }
            checkUserLoginStatus()
        }

        binding.btnTimur.setOnClickListener {
            with(sharedPref.edit()) {
                putString("location", "jaktim")
                apply()
            }
            checkUserLoginStatus()
        }

        binding.btnUtara.setOnClickListener {
            with(sharedPref.edit()) {
                putString("location", "jakut")
                apply()
            }
            checkUserLoginStatus()
        }
    }

    private fun checkUserLoginStatus() {
        if(auth.currentUser != null) {
            // user already signed in
            navigateToMainActivity()
        } else {
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}