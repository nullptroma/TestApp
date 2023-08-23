package com.example.testapp.domain

data class CryptosPackage(
    val data: List<CryptoData> = listOf(),
    val loading: Boolean = false,
    val error: Boolean = false
)
