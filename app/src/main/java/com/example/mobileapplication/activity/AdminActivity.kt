package com.example.mobileapplication.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.R
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val logoutButton = findViewById<Button>(R.id.logoutButton)

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Optional: Setup future button actions
        val manageMenuBtn = findViewById<Button>(R.id.manageMenuBtn)
        val viewOrdersBtn = findViewById<Button>(R.id.viewOrdersBtn)
        val addCoffeeBtn = findViewById<Button>(R.id.addCoffeeBtn)

        manageMenuBtn.setOnClickListener {
            Toast.makeText(this, "Manage Menu (coming soon)", Toast.LENGTH_SHORT).show()
        }

        viewOrdersBtn.setOnClickListener {
            Toast.makeText(this, "View Orders (coming soon)", Toast.LENGTH_SHORT).show()
        }

        addCoffeeBtn.setOnClickListener {
            Toast.makeText(this, "Add Coffee (coming soon)", Toast.LENGTH_SHORT).show()
        }
    }
}
