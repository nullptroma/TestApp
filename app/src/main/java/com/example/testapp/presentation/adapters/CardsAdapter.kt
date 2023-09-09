package com.example.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CardFrameBinding
import com.example.testapp.domain.models.cardsettings.EnabledCard
import com.example.testapp.presentation.viewholders.CardViewHolder

class CardsAdapter(private val _cardList: List<EnabledCard>) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardFrameBinding.inflate(inflater, parent, false)

        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(_cardList[position])
    }
}