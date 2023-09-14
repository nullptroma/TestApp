package com.example.testapp.presentation.cards.peresenters

import android.view.View
import com.example.testapp.databinding.WeatherCardLayoutBinding
import com.example.testapp.domain.models.cards.WeatherCardState
import com.example.testapp.domain.models.cards_callbacks.WeatherCallback

class WeatherCardView(
    state: WeatherCardState,
    callback: WeatherCallback,
    private val _binding: WeatherCardLayoutBinding
) {
    val root
        get() = _binding.root

    init {
        val data = state.data
        val loading = data == null
        _binding.loadingWeather.visibility = if(loading) View.VISIBLE else View.GONE
        _binding.contentWeather.visibility = if(loading) View.GONE else View.VISIBLE
        if(data != null) {
            _binding.city.text = data.city
        }

    }
}