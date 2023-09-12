package com.example.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CityItemBinding
import com.example.testapp.domain.models.city.CityInfo
import com.example.testapp.presentation.viewholders.CityViewHolder

class CitiesAdapter(private val _cityList: List<CityInfo>) : RecyclerView.Adapter<CityViewHolder>() {
    var onClick : ((CityInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)

        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _cityList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(_cityList[position])
        holder.callback = {
            onClick?.invoke(it)
        }
    }
}