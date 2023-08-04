package com.example.testapp.presentation.cards

import androidx.lifecycle.ViewModel
import com.example.testapp.domain.Cards
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class CardViewModel: ViewModel() {
    abstract val id: Long
    abstract val cardType: Cards
}
