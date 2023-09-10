package com.example.testapp.presentation.states.screens

import com.example.testapp.domain.models.CityInfo

data class SelectCityScreenState(val cities: List<CityInfo> = listOf(), val exit:Boolean = false)