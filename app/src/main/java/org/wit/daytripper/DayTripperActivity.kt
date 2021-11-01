package org.wit.daytripper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber


class DayTripperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daytripper)

        //Logging
        Timber.plant(Timber.DebugTree())
        Timber.i ("Day Tripper Activity started..")
    }
}