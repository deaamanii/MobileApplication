package com.example.mobileapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mobileapplication.Adapter.CategoryAdapter
import com.example.mobileapplication.Adapter.PopularAdapter
import com.example.mobileapplication.CartActivity
import com.example.mobileapplication.ViewModel.MainViewModel
import com.example.mobileapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()
    private var userType: String? = null  // <-- Check if admin or user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve user type from intent (e.g., "admin" or "user")
        userType = intent.getStringExtra("userType")

        // Optional: Hide certain UI for admins
        if (userType == "admin") {
            Toast.makeText(this, "Admin Mode Active", Toast.LENGTH_SHORT).show()
            // Example: binding.bottomNavigationView.visibility = View.GONE
        }

        initBanner()
        initCategory()
        initPopular()
        initBottomMenu()
        setupProfileButton()
        setupLogoutButton()
    }

    private fun initBottomMenu(){
        binding.cartBtn.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setupProfileButton() {
        binding.profileButton.setOnClickListener {
            if (userType == "admin") {
                // Navigate to AdminActivity
                startActivity(Intent(this, AdminActivity::class.java))
            } else {
                // Navigate to user profile
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
    }

    private fun setupLogoutButton() {
        binding.logouttButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.loadBanner().observeForever {
            Glide.with(this@MainActivity)
                .load(it[0].url)
                .into(binding.banner)
            binding.progressBarBanner.visibility = View.GONE
        }
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.loadCategory().observeForever {
            binding.recyclerViewCat.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )
            binding.recyclerViewCat.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        }
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observeForever {
            binding.recyclerViewPopular.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        }
    }
}
