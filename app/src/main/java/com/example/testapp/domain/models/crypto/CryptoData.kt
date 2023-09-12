package com.example.testapp.domain.models.crypto

data class CryptoData (
    val id: String="",
    val symbol: String="",
    val imageUrl: String="",
    val price: Double=0.0,
    val name: String="",
    val change24H: Double=0.0,
)
