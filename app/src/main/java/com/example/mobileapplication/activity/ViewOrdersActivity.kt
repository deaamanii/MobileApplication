package com.example.mobileapplication.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R
import com.google.firebase.database.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ViewOrdersActivity : AppCompatActivity() {

    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders)

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish() // Go back to AdminActivity
        }

        ordersRecyclerView = findViewById(R.id.ordersRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(this)
        ordersAdapter = OrdersAdapter(ordersList)
        ordersRecyclerView.adapter = ordersAdapter

        databaseRef = FirebaseDatabase.getInstance().getReference("orders")

        fetchOrders()
    }

    private fun fetchOrders() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    order?.let {
                        it.id = orderSnapshot.key
                        ordersList.add(it)
                    }
                }
                ordersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewOrdersActivity, "Failed to load orders.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    data class Order(
        var id: String? = null,
        var status: String? = "Pending",
        var total: Double = 0.0
    )
//test
    inner class OrdersAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(view)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            val order = orders[position]
            holder.statusText.text = "Status: ${order.status}"
            holder.totalText.text = "Total: $${order.total}"

            if (order.status == "Accepted") {
                holder.acceptButton.visibility = View.GONE // 🔥 hide the button
            } else {
                holder.acceptButton.visibility = View.VISIBLE
                holder.acceptButton.setOnClickListener {
                    order.id?.let {
                        databaseRef.child(it).child("status").setValue("Accepted")
                        Toast.makeText(this@ViewOrdersActivity, "Order accepted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        override fun getItemCount() = orders.size

        inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val statusText: TextView = view.findViewById(R.id.statusText)
            val totalText: TextView = view.findViewById(R.id.totalText)
            val acceptButton: Button = view.findViewById(R.id.acceptButton)
        }
    }
}