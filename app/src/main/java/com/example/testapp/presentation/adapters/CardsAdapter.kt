package com.example.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CardFrameBinding
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.cards.CardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.presentation.viewholders.CardViewHolder

class CardsAdapter(
    private val _callbacks: Map<CardType, ICardCallback?>, private val _onSettings: (Long) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {
    val list = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Pair<Long, CardState>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Long, CardState>, newItem: Pair<Long, CardState>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<Long, CardState>, newItem: Pair<Long, CardState>
        ): Boolean {
            return oldItem == newItem
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardFrameBinding.inflate(inflater, parent, false)

        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.currentList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val pair = list.currentList[position]
        holder.bind(pair.first, pair.second, _callbacks[pair.second.type])
        holder.onSetting = _onSettings
    }
}