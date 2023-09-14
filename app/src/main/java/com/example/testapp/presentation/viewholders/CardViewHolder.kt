package com.example.testapp.presentation.viewholders

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.CardFrameBinding
import com.example.testapp.databinding.CryptoCardLayoutBinding
import com.example.testapp.databinding.MapCardLayoutBinding
import com.example.testapp.databinding.UnsettedCardBinding
import com.example.testapp.databinding.WeatherCardLayoutBinding
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.cards.CardState
import com.example.testapp.domain.models.cards.CryptoCardState
import com.example.testapp.domain.models.cards.MapCardState
import com.example.testapp.domain.models.cards.WeatherCardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cards_callbacks.WeatherCallback
import com.example.testapp.presentation.cards.peresenters.CryptoCardView
import com.example.testapp.presentation.cards.peresenters.MapCardView
import com.example.testapp.presentation.cards.peresenters.WeatherCardView

class CardViewHolder(private val _binding: CardFrameBinding) :
    RecyclerView.ViewHolder(_binding.root) {
    var onSetting: (id: Long) -> Unit = {}
    private var _old : View? = null

    fun bind(id: Long, state: CardState, callback: ICardCallback?) {
        _binding.cardTypeText.text = state.type.text
        _binding.settingsButtonOnCardframe.setOnClickListener {
            onSetting(id)
        }
        val inflater = LayoutInflater.from(_binding.root.context)

        var view: View? = null
        if (state.needSettings) {
            val bind = UnsettedCardBinding.inflate(inflater)
            bind.settingsButton.setOnClickListener {
                onSetting(id)
            }
            view = bind.root
        }
        else {
            when(state.type) {
                CardType.MAP -> {
                    val map = MapCardView(state as MapCardState, MapCardLayoutBinding.inflate(inflater))
                    view = map.root
                }
                CardType.WEATHER -> {
                    if(callback is WeatherCallback) {
                        val weather = WeatherCardView(state as WeatherCardState, callback, WeatherCardLayoutBinding.inflate(inflater))
                        view = weather.root
                    }
                }
                CardType.CRYPTO -> {
                    val crypto = CryptoCardView(state as CryptoCardState, CryptoCardLayoutBinding.inflate(inflater))
                    view = crypto.root
                }
            }
        }

        if (view != null){
            _binding.cardContentFrame.addView(view)
            if(_old != null)
                _binding.cardContentFrame.removeView(_old)
            _old = view
        }
    }
}