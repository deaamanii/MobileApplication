package com.example.mobileapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileapplication.Adapter.CartAdapter
import com.example.mobileapplication.Domain.ItemsModel
import com.example.mobileapplication.Helper.ManagementCart
import com.example.mobileapplication.Helper.ChangeNumberItemsListener
import com.example.mobileapplication.databinding.ActivityCartBinding
import com.google.firebase.database.FirebaseDatabase

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        calculateCart()
        setVariable()
        initCartList()
    }

    private fun initCartList() {
        binding.apply {
            cartView.layoutManager = LinearLayoutManager(
                this@CartActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            cartView.adapter = CartAdapter(
                managementCart.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }
                }
            )
        }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 15
        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100.0
        val itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100.0

        binding.apply {
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }

    private fun setVariable() {
        binding.button5.setOnClickListener {
            val cartItems = managementCart.getListCart()
            val databaseRef = FirebaseDatabase.getInstance().reference

            val orderMap = hashMapOf<String, Any>(
                "items" to cartItems,
                "subtotal" to managementCart.getTotalFee(),
                "tax" to tax,
                "delivery" to 15,
                "total" to (managementCart.getTotalFee() + tax + 15),
                "status" to "Pending"
            )

            // Write order to Firebase
            val newOrderRef = databaseRef.child("orders").push()
            newOrderRef.setValue(orderMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Coffee is on the way...", Toast.LENGTH_LONG).show()
                    // Optional: clear cart
                    managementCart.clearCart()
                    finish() // Go back or close cart
                } else {
                    Toast.makeText(this, "Failed to submit order", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}