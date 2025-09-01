package com.correxapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CorrexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("CorrexApplication", "CorrexAPP iniciado com sucesso")
    }
}
