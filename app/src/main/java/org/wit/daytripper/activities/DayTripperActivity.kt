package org.wit.daytripper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.daytripper.databinding.ActivityDaytripperBinding
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import timber.log.Timber.i


class DayTripperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaytripperBinding
    var dayTrip = DayTripperModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaytripperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Day Trip Activity started...")
        //Add
        binding.btnAdd.setOnClickListener() {
            dayTrip.title = binding.dayTripTitle.text.toString()
            dayTrip.description = binding.description.text.toString()
            if (dayTrip.title.isNotEmpty()) {
                app.dayTrips.add(dayTrip.copy())
                i("add Button Pressed: ${dayTrip}")
                for (i in app.dayTrips.indices)
                    { i("DayTrip[$i]:${app.dayTrips[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a day trip name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}