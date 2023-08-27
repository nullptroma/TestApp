package com.example.testapp

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestApplication : Application() {
    init {
        MapKitFactory.setApiKey("a8f48bfa-1c00-4489-a68b-01ccdec53c8e")
    }
}