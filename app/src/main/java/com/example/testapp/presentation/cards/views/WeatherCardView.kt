package com.example.testapp.presentation.cards.views

import com.example.testapp.databinding.WeatherCardLayoutBinding
import com.example.testapp.domain.models.cards_callbacks.WeatherCallback

class WeatherCardView(
    id: Long,
    callback: WeatherCallback,
    private val _binding: WeatherCardLayoutBinding
) {
    val root
        get() = _binding.root

    init {
        _binding.weatherName.text = "Hello!!!!!"
        _binding.weatherName.setOnClickListener {
            callback.refresh(id)
        }
    }
}