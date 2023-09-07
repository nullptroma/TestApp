package com.example.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.EnabledCardItemBinding
import com.example.testapp.domain.models.cardsettings.EnabledCard
import com.example.testapp.presentation.viewholders.EnabledCardViewHolder

class EnabledCardsAdapter : RecyclerView.Adapter<EnabledCardViewHolder>() {
    var onRemove : ((EnabledCard) -> Unit)? = null
    var onStartDrag : ((EnabledCardViewHolder) -> Unit)? = null

    private val differCallback = object: DiffUtil.ItemCallback<EnabledCard>() {
        override fun areItemsTheSame(oldItem: EnabledCard, newItem: EnabledCard): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EnabledCard, newItem: EnabledCard): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnabledCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EnabledCardItemBinding.inflate(inflater, parent, false)

        return EnabledCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EnabledCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.onRemove = onRemove
        holder.onStartDrag = onStartDrag
    }

    fun moveItem(from: Int, to: Int) {
        val list = differ.currentList.toMutableList()
        val item = list.removeAt(from)
        list.add(to, item)
        differ.submitList(list)
    }
}