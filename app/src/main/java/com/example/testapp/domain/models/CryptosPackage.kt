package com.example.testapp.domain.models

data class CryptosPackage(
    val data: List<CryptoData> = listOf(),
    val loading: Boolean = false,
    val error: Boolean = false
)
