package org.wit.daytripper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.daytripper.databinding.ActivityDaytripperBinding
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import timber.log.Timber.i


class DayTripperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaytripperBinding
    var dayTrip = DayTripperModel()
    val dayTrips = ArrayList<DayTripperModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaytripperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Day Trip Activity started...")

        //Add
        binding.btnAdd.setOnClickListener() {
            dayTrip.title = binding.dayTripTitle.text.toString()
            dayTrip.description = binding.description.text.toString()
            if (dayTrip.title.isNotEmpty()) {
                dayTrips.add(dayTrip.copy())
                i("add Button Pressed: $dayTrip")
                for (i in dayTrips.indices)
                { i("DayTrip[$i]:${this.dayTrips[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a day trip name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}