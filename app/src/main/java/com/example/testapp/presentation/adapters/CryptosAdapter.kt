package com.example.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CryptoItemBinding
import com.example.testapp.presentation.states.recycler_view.CryptoItemData
import com.example.testapp.presentation.viewholders.CryptoViewHolder


class CryptosAdapter : RecyclerView.Adapter<CryptoViewHolder>(), Filterable {
    var onChangeSelect: (String) -> Unit = { }

    private val list = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CryptoItemData>() {
        override fun areItemsTheSame(
            oldItem: CryptoItemData, newItem: CryptoItemData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CryptoItemData, newItem: CryptoItemData
        ): Boolean {
            return oldItem == newItem
        }
    })
    private var fullList = listOf<CryptoItemData>()

    fun submit(newList : List<CryptoItemData>, filter: String = "") {
        fullList = newList.toList()
        getFilter().filter(filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CryptoItemBinding.inflate(inflater, parent, false)

        return CryptoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.currentList.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(list.currentList[position])
        holder.onChangeSelect = onChangeSelect
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
                list.submitList(results?.values as List<CryptoItemData>)
            }
        }
    }
}