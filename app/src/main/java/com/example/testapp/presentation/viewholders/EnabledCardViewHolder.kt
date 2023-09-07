package com.example.testapp.presentation.viewholders

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.EnabledCardItemBinding
import com.example.testapp.domain.models.cardsettings.EnabledCard


class EnabledCardViewHolder(private val _binding: EnabledCardItemBinding) :
    RecyclerView.ViewHolder(_binding.root) {
    var onRemove: ((EnabledCard) -> Unit)? = null
    var onStartDrag: ((EnabledCardViewHolder) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    fun bind(data: EnabledCard) {
        _binding.enabledCardName.text = data.type.text
        _binding.removeCardButton.setOnClickListener {
            onRemove?.invoke(data)
        }
        _binding.dragHandlerImage.setOnTouchListener { _, event ->
            if (event != null && event.action == MotionEvent.ACTION_DOWN)
                onStartDrag?.invoke(this)
            false
        }
    }
}