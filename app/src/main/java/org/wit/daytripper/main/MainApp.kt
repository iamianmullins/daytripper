package org.wit.daytripper.main

import android.app.Application
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val dayTrips = ArrayList<DayTripperModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("DayTripper started")
        dayTrips.add(DayTripperModel("Trip One", "About one..."))
        dayTrips.add(DayTripperModel("Trip Two", "About two..."))
        dayTrips.add(DayTripperModel("Trip Three", "About three..."))
    }
}