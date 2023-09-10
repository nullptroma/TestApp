package com.example.testapp.presentation.cards.views

import com.example.testapp.databinding.WeatherCardLayoutBinding

class WeatherCardView(private val _binding : WeatherCardLayoutBinding)  {
    val root
        get() = _binding.root

    init {
        _binding.weatherName.text = "Hello!!!!!"
    }
}