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

        // Logout button
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Manage Menu button (currently placeholder)
        val manageMenuBtn = findViewById<Button>(R.id.manageMenuBtn)
        manageMenuBtn.setOnClickListener {
            Toast.makeText(this, "Manage Menu (coming soon)", Toast.LENGTH_SHORT).show()
        }

        // View Orders button (currently placeholder)
        val viewOrdersBtn = findViewById<Button>(R.id.viewOrdersBtn)
        viewOrdersBtn.setOnClickListener {
            Toast.makeText(this, "View Orders (coming soon)", Toast.LENGTH_SHORT).show()
        }

        // Add New Coffee button - launches MainActivity
        val addCoffeeBtn = findViewById<Button>(R.id.addCoffeeBtn)
        addCoffeeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
