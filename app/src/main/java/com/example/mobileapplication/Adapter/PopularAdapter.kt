package com.example.mobileapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileapplication.Domain.ItemsModel
import com.example.mobileapplication.activity.DetailActivity
import com.example.mobileapplication.databinding.ViewholderPopularBinding

class PopularAdapter(val items: MutableList<ItemsModel>) : RecyclerView.Adapter<PopularAdapter.Viewholder>() {
    lateinit var context: Context

    class Viewholder(val binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]

        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "$${item.price}"

        Glide.with(context)
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        // ✅ Use the correct key here ("object" not "Object")
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}