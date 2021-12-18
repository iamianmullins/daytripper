package org.wit.daytripper.main

import android.app.Application
import org.wit.daytripper.models.DayTripStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //lateinit var dayTripStore: DayTripStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //dayTripStore = DayTripMemStore()
        i("DayTripper started")
    }
}