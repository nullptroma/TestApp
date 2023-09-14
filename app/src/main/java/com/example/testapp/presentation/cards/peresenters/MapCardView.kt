package com.example.testapp.presentation.cards.peresenters

import com.example.testapp.databinding.MapCardLayoutBinding
import com.example.testapp.domain.models.cards.MapCardState
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

class MapCardView(
    state: MapCardState,
    private val _binding: MapCardLayoutBinding
) {
    val root
        get() = _binding.root

    init {
        val coordinates = state.coordinates
        val map = _binding.map
        map.map.isScrollGesturesEnabled = false
        map.map.isZoomGesturesEnabled = false
        map.map.isFastTapEnabled = false
        map.map.isRotateGesturesEnabled = false
        map.map.isTiltGesturesEnabled = false
        map.map.move(
            CameraPosition(Point(coordinates.latitude, coordinates.longitude), 9.5f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f),
            null
        )
    }
}