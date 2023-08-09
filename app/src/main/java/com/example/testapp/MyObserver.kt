package com.example.testapp

import androidx.lifecycle.Observer

class MyObserver<T>(private val callback: (T)->Unit) : Observer<T> {
    override fun onChanged(value: T) {
        callback(value)
    }
}