package com.example.testapp.presentation.cards.peresenters

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.databinding.CryptoCardLayoutBinding
import com.example.testapp.databinding.CryptoInfoCardBinding
import com.example.testapp.domain.models.cards.CryptoCardState
import com.example.testapp.domain.models.crypto.CryptoData

class CryptoCardView(
    state: CryptoCardState,
    private val _binding: CryptoCardLayoutBinding
) {
    val root
        get() = _binding.root

    init {
        if(!state.error) {
            _binding.cards.visibility = View.VISIBLE
            _binding.error.visibility = View.GONE
            val list = listOf(_binding.card0, _binding.card1, _binding.card2)
            for (i in 0 until state.info.size) {
                show(list[i], state.info[i], state.loading)
            }
            for (i in state.info.size until list.size)
                list[i].root.visibility = View.GONE
        }
        else {
            _binding.cards.visibility = View.GONE
            _binding.error.visibility = View.VISIBLE
        }
    }

    private fun show(binding: CryptoInfoCardBinding, data: CryptoData, loading: Boolean) {
        binding.cryptoName.text = data.name
        Glide.with(binding.root).load(data.imageUrl).into(binding.circleImageView)
        if (loading) {
            binding.prices.visibility = View.GONE
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.prices.visibility = View.VISIBLE
            binding.progressCircular.visibility = View.GONE
            binding.price.text =
                String.format("%.4f", data.price).removeSuffix("0").removeSuffix("0") + "$"
            binding.priceChange.text =
                String.format("%.4f", data.change24H).removeSuffix("0").removeSuffix("0")
            if (data.change24H < 0) {
                binding.priceChange.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.negative_color
                    )
                )
                binding.arrowDown.visibility = View.VISIBLE
                binding.arrowUp.visibility = View.GONE
            }
        }
    }
}