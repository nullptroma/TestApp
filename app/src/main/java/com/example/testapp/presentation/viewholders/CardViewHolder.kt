package com.example.testapp.presentation.viewholders

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CardFrameBinding
import com.example.testapp.databinding.UnsettedCardBinding
import com.example.testapp.databinding.WeatherCardLayoutBinding
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.cards.CardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cards_callbacks.WeatherCallback
import com.example.testapp.presentation.cards.views.WeatherCardView

class CardViewHolder(private val _binding: CardFrameBinding) :
    RecyclerView.ViewHolder(_binding.root) {
    var onSetting: (id: Long) -> Unit = {}

    fun bind(id: Long, data: CardState, callback: ICardCallback?) {
        _binding.cardTypeText.text = data.type.text
        _binding.settingsButtonOnCardframe.setOnClickListener {
            onSetting(id)
        }
        val inflater = LayoutInflater.from(_binding.root.context)

        var view: View? = null
        if (data.needSettings) {
            val bind = UnsettedCardBinding.inflate(inflater)
            bind.settingsButton.setOnClickListener {
                onSetting(id)
            }
            view = bind.root
        }
        else {
            when(data.type) {
                CardType.MAP -> {

                }
                CardType.WEATHER -> {
                    if(callback is WeatherCallback) {
                        val weather = WeatherCardView(id, callback, WeatherCardLayoutBinding.inflate(inflater))
                        view = weather.root
                    }
                }
                CardType.CRYPTO -> {

                }
            }
        }

        if (view != null)
            _binding.cardContentFrame.addView(view)
    }
}