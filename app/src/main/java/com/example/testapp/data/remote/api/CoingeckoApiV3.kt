package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.model.RemoteCrypto
import retrofit2.http.GET

interface CoingeckoApiV3 {
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc")
    suspend fun getCrypto(): List<RemoteCrypto>
}