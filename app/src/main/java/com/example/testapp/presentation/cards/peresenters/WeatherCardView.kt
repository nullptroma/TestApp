package com.example.testapp.presentation.cards.peresenters

import android.view.View
import com.bumptech.glide.Glide
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
        _binding.loadingWeather.visibility = if (loading) View.VISIBLE else View.GONE
        _binding.contentWeather.visibility = if (loading) View.GONE else View.VISIBLE
        if (data != null) {
            _binding.weatherText.isSelected = true
            _binding.feelsLikePrompt.isSelected = true
            _binding.marquee1.isSelected = true
            _binding.marquee2.isSelected = true
            _binding.marquee3.isSelected = true
            _binding.marquee4.isSelected = true

            _binding.city.text = data.city
            _binding.refresh.setOnClickListener {
                callback.refresh(state.id)
            }
            Glide.with(_binding.root).load("https://openweathermap.org/img/w/${data.iconUrl}.png")
                .into(_binding.weatherIco)
            _binding.weatherText.text = data.desc.let { it[0].uppercase() + it.substring(1) }
            _binding.temp.text = "${String.format("%.1f", data.temp - 273, 15)} °C"
            _binding.feelsLike.text = "${String.format("%.1f", data.tempFeels - 273, 15)} °C"
            _binding.wind.text = String.format("%.2f", data.windSpeed) + " м/с"
            _binding.pressure.text = String.format("%.2f", data.pressure.toDouble() * 0.750064) + "м.р.ст"
            _binding.humidity.text = data.humidity.toString() + "%"
            _binding.cloudy.text = data.clouds.toString() + "%"
        }
    }
}