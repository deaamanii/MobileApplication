package com.example.mobileapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.mobileapplication.Domain.ItemsModel
import com.example.mobileapplication.Helper.ManagementCart
import com.example.mobileapplication.R
import com.example.mobileapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailBinding
    private lateinit var item:ItemsModel
    private lateinit var managmentCart:ManagementCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart=ManagementCart(this)

        bundle()
        initSizeList()
    }

    private fun initSizeList() {
        binding.apply {
          smallBtn.setOnClickListener {
               smallBtn.setBackgroundResource(R.drawable.stroke_brown_bg)
               mediumBtn.setBackgroundResource(0)
                LargeBtn.setBackgroundResource(0)
          }
            mediumBtn.setOnClickListener {
                smallBtn.setBackgroundResource(0)
                mediumBtn.setBackgroundResource(R.drawable.stroke_brown_bg)
                LargeBtn.setBackgroundResource(0)
            }
            LargeBtn.setOnClickListener {
                smallBtn.setBackgroundResource(0)
                mediumBtn.setBackgroundResource(0)
                LargeBtn.setBackgroundResource(R.drawable.stroke_brown_bg)
            }

        }
    }

    private fun bundle() {
        binding.apply {
           item= intent.getSerializableExtra("Object") as ItemsModel

            Glide.with(this@DetailActivity)
                .load(item.picUrl[0])
                .into(binding.picMain)

            titleTxt.text=item.title
            descriptionTxt.text=item.description
            priceTxt.text="$"+item.price
            ratingTxt.text=item.rating.toString()

            addToCardbtn.setOnClickListener{
            item.numberInCart=Integer.valueOf(
              numberItemTxt.text.toString()
            )
            managmentCart.insertItems(item)
        }
            backBtn.setOnClickListener{
                finish()
            }
            plusCart.setOnClickListener{
                numberItemTxt.text=(item.numberInCart+1).toString()
                item.numberInCart++
            }
            minusBtn.setOnClickListener {
                if(item.numberInCart>0) {
                    numberItemTxt.text=(item.numberInCart-1).toString()
                    item.numberInCart--
                }
                }
            }
        }
    }
