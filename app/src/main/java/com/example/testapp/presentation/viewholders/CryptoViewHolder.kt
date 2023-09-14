package com.example.testapp.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.databinding.CryptoItemBinding
import com.example.testapp.presentation.states.recycler_view.CryptoItemData

class CryptoViewHolder(private val _binding: CryptoItemBinding) :
    RecyclerView.ViewHolder(_binding.root) {
    var onChangeSelect: (String) -> Unit = {}

    fun bind(data: CryptoItemData) {
        Glide.with(_binding.root).load(data.imageUrl).into(_binding.cryptoIcon)
        _binding.cryptoName.text = data.name
        _binding.cryptoSelected.isChecked = data.enabled
        _binding.cryptoSelected.setOnClickListener {
            onChangeSelect(data.id)
        }
    }
}