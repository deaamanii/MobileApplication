package com.example.mobileapplication.Helper

import android.content.Context
import android.widget.Toast
import com.example.mobileapplication.Domain.ItemsModel

class ManagementCart(private val context: Context) {

    private val tinyDB = TinyDB(context)

    fun insertItems(item: ItemsModel) {
        val listItem = getListCart()
        val existAlready = listItem.any { it.title == item.title }
        val index = listItem.indexOfFirst { it.title == item.title }

        if (existAlready) {
            listItem[index].numberInCart = item.numberInCart
        } else {
            listItem.add(item)
        }

        tinyDB.putListObject("CartList", listItem)
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun minusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listItems[position].numberInCart == 1) {
            listItems.removeAt(position)
        } else {
            listItems[position].numberInCart--
        }
        tinyDB.putListObject("CartList", listItems)
        listener.onChanged()
    }

    fun removeItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listItems.removeAt(position)
        tinyDB.putListObject("CartList", listItems)
        listener.onChanged()
    }

    fun plusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listItems[position].numberInCart++
        tinyDB.putListObject("CartList", listItems)
        listener.onChanged()
    }

    fun getTotalFee(): Double {
        return getListCart().sumOf { it.price * it.numberInCart }
    }

    fun clearCart() {
        tinyDB.putListObject("CartList", arrayListOf())
    }
}