package com.example.mobileapplication.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.Domain.ItemsModel
import com.example.mobileapplication.R
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // Logout button
        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Manage Menu button - opens a pre-filled Cappuccino detail screen
        findViewById<Button>(R.id.manageMenuBtn).setOnClickListener {
            val cappuccinoItem = ItemsModel(
                title = "Cappuccino",
                description = "Cappuccino is a traditional Italian coffee drink made with equal parts espresso, steamed milk, and milk foam. It has a strong espresso flavor, balanced by the creamy texture of the steamed milk and the light, airy foam on top.",
                picUrl = arrayListOf("https://firebasestorage.googleapis.com/v0/b/project195-aa33b.appspot.com/o/Cappoccino.jpg?alt=media&token=0e553cec-a35b-499b-932e-dac928036209"), // Replace with real image
                price = 24.0,
                rating = 4.6,
                numberInCart = 1
            )

            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("Object", cappuccinoItem)
            }
            startActivity(intent)
        }

        // View Orders button - placeholder
        findViewById<Button>(R.id.viewOrdersBtn).setOnClickListener {
            startActivity(Intent(this, ViewOrdersActivity::class.java))
        }

        // Add New Coffee button - opens MainActivity
        findViewById<Button>(R.id.addCoffeeBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userType", "admin") // ✅ THIS LINE IS THE FIX
            startActivity(intent)
        }

    }
}