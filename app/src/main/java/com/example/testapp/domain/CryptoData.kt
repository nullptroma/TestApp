package com.example.testapp.domain

data class CryptoData (
    val id: String,
    val symbol: String,
    val imageUrl: String,
    val price: Double,
    val name: String,
    val change24H: Double,
)
