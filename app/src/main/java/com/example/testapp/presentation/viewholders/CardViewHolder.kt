package com.example.testapp.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CardFrameBinding
import com.example.testapp.domain.models.cardsettings.EnabledCard

class CardViewHolder(private val _binding: CardFrameBinding) : RecyclerView.ViewHolder(_binding.root) {
    init {

    }

    fun bind(data: EnabledCard) {
        _binding.cardTypeText.text = data.type.text

    }
}