package com.example.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CityItemBinding
import com.example.testapp.domain.models.city.CityInfo
import com.example.testapp.presentation.viewholders.CityViewHolder


class CitiesAdapter : RecyclerView.Adapter<CityViewHolder>(), Filterable {
    var onClick: ((CityInfo) -> Unit)? = null
    private val list = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CityInfo>() {
        override fun areItemsTheSame(
            oldItem: CityInfo, newItem: CityInfo
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CityInfo, newItem: CityInfo
        ): Boolean {
            return oldItem == newItem
        }
    })
    private var fullList = listOf<CityInfo>()

    fun submit(newList : List<CityInfo>, filter: String = "") {
        fullList = newList.toList()
        getFilter().filter(filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)

        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.currentList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(list.currentList[position])
        holder.callback = {
            onClick?.invoke(it)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().lowercase()
                val filtered = fullList.filter { it.name.lowercase().contains(charSearch) }

                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list.submitList(results?.values as List<CityInfo>)
            }
        }
    }
}