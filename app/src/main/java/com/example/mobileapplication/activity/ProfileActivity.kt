package com.example.mobileapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Optional: get logged-in username from intent
        // val username = intent.getStringExtra("username")
    }
}
