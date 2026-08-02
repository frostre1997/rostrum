package com.rostrumpodcast.rostrum

import android.app.Application
import androidx.work.Configuration

class App : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().build()
    }
    override fun onCreate() {
        super.onCreate()
        // TODO: initialize workers
    }
}
