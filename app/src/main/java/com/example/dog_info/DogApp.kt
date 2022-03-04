package com.example.dog_info

import android.app.Application
import com.example.dog_info.data.database.DbFactory

class DogApp : Application() {

    override fun onCreate() {
        DbFactory.init(this.applicationContext)
        super.onCreate()
    }
}