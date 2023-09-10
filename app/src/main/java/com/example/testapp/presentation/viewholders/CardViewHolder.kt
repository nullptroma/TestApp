package com.example.testapp.presentation.viewholders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CardFrameBinding
import com.example.testapp.databinding.WeatherCardLayoutBinding
import com.example.testapp.domain.models.cardsettings.EnabledCard
import com.example.testapp.presentation.cards.views.WeatherCardView

class CardViewHolder(private val _binding: CardFrameBinding) :
    RecyclerView.ViewHolder(_binding.root) {

    fun bind(data: EnabledCard) {
        _binding.cardTypeText.text = data.type.text
        val inflater = LayoutInflater.from(_binding.root.context)

        val weather = WeatherCardView(WeatherCardLayoutBinding.inflate(inflater))
        _binding.cardContentFrame.addView(weather.root)
    }
}