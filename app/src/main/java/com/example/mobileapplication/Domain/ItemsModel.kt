package com.example.mobileapplication.Domain

import java.io.Serializable

/**
 * Represents a single coffee item in the app.
 */
data class ItemsModel(
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = arrayListOf(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0,
    var extra: String = ""
) : Serializable
