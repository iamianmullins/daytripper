package org.wit.daytripper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.daytripper.R
import org.wit.daytripper.databinding.ActivityDaytripperBinding
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import timber.log.Timber.i


class DayTripperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaytripperBinding
    var dayTrip = DayTripperModel()
    var edit = false
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaytripperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Day Trip Activity started...")

        if (intent.hasExtra("daytrip_edit")) {
            edit = true
            dayTrip = intent.extras?.getParcelable("daytrip_edit")!!
            binding.dayTripTitle.setText(dayTrip.title)
            binding.description.setText(dayTrip.description)
            binding.btnAdd.setText(R.string.save_daytrip)
        }

        //Add
        binding.btnAdd.setOnClickListener() {
            dayTrip.title = binding.dayTripTitle.text.toString()
            dayTrip.description = binding.description.text.toString()
            if (dayTrip.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_daytrip_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.dayTrips.update(dayTrip.copy())
                } else {
                    app.dayTrips.create(dayTrip.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_daytrip, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}