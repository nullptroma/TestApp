package com.example.testapp.presentation.screens.select_city

import com.example.testapp.domain.CityInfo

data class SelectCityScreenState(val cities: List<CityInfo> = listOf(), val exit:Boolean = false)