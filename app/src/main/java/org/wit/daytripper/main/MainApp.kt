package org.wit.daytripper.main

import android.app.Application
import org.wit.daytripper.models.DayTripJSONStore
import org.wit.daytripper.models.DayTripMemStore
import org.wit.daytripper.models.DayTripStore
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val dayTrips = ArrayList<DayTripperModel>()
    //val dayTrips = DayTripMemStore()
    lateinit var dayTrips: DayTripStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        dayTrips = DayTripJSONStore(applicationContext)
        i("DayTripper started")
    }
}