package com.example.testapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.testapp.domain.models.Coordinates
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMap(coordinates: Coordinates) {

    AndroidView(modifier = Modifier
        .fillMaxSize()
        .height(200.dp).shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ),
        factory = { context ->
            MapView(context)
        }, update = {
            it.map.isScrollGesturesEnabled = false
            it.map.isZoomGesturesEnabled = false
            it.map.move(
                CameraPosition(Point(coordinates.latitude, coordinates.longitude), 9.5f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 2f),
                null
            )
        })
}