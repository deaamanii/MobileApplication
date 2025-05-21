package com.example.mobileapplication.Adapter

import android.content.Context
import android.content.Intent
import android.media.Rating
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileapplication.Domain.ItemsModel
import com.example.mobileapplication.activity.DetailActivity
import com.example.mobileapplication.databinding.ViewholderItemPicLeftBinding
import com.example.mobileapplication.databinding.ViewholderItemPicRightBinding

class ItemsListCategoryAdapter(val items:MutableList<ItemsModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object{
            const val Type_ITEM1=0
            const val Type_ITEM2=1

        }
    lateinit var context:Context
    override fun getItemViewType(position: Int): Int {
        return if(position%2==0) Type_ITEM1 else Type_ITEM2
    }
    class ViewholderITEm1(val binding: ViewholderItemPicRightBinding):
    RecyclerView.ViewHolder(binding.root)

    class ViewholderITEm2(val binding: ViewholderItemPicLeftBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       context=parent.context
        return  when(viewType){
            Type_ITEM1-> {
                val binding=ViewholderItemPicRightBinding.inflate(
                    LayoutInflater.from(context),
                    parent,  false
                )
                ViewholderITEm1(binding)
            }
            Type_ITEM2->{
                val binding=ViewholderItemPicLeftBinding.inflate(
                    LayoutInflater.from(context),
                    parent,  false
                )
                ViewholderITEm2(binding)
            }
            else->throw IllegalArgumentException("Invalidview")
        }
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     val item=items[position]
        fun bindCommonData(
            titleTxt:String,
            priceTxt:String,
            rating:Float,
            picUrl:String
        ){
            when(holder){
                is ViewholderITEm1->{
                    holder.binding.titleTxt.text=titleTxt
                    holder.binding.priceTxt.text = priceTxt
                    holder.binding.ratingBar6.rating=rating

                    Glide.with(context)
                        .load(picUrl)
                        .into(holder.binding.picMain)


                    holder.itemView.setOnClickListener {
                        val intent=Intent(context,DetailActivity::class.java)
                        intent.putExtra("object", items[position])
                        context.startActivity(intent)

                    }

                }
                is ViewholderITEm2->{
                    holder.binding.titleTxt.text=titleTxt
                    holder.binding.priceTxt.text = priceTxt
                    holder.binding.ratingBar6.rating=rating

                    Glide.with(context)
                        .load(picUrl)
                        .into(holder.binding.picMain)


                    holder.itemView.setOnClickListener {
                        val intent=Intent(context,DetailActivity::class.java)
                        intent.putExtra("object", items[position])
                        context.startActivity(intent)

                    }

                }


            }
        }
        bindCommonData(
            item.title,
            priceTxt = "${item.price} USD",
            item.rating.toFloat(),
            item.picUrl[0]
        )
    }
}