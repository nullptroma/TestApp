package com.example.testapp.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CityItemBinding
import com.example.testapp.domain.models.CityInfo

class CityViewHolder(private val _binding: CityItemBinding) : RecyclerView.ViewHolder(_binding.root) {
    var callback : ((CityInfo) -> Unit)? = null

    fun bind(data: CityInfo) {
        _binding.countryCode.text = data.country
        _binding.cityName.text = data.name
        _binding.root.setOnClickListener {
            callback?.invoke(data)
        }
    }
}