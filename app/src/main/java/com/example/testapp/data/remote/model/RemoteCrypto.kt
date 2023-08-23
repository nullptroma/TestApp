package com.example.testapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCrypto(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("current_price") val price: Double,
    @SerializedName("price_change_24h") val change24H: Double,
)
