package org.wit.daytripper.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.daytripper.R
import org.wit.daytripper.databinding.ActivityDaytripperBinding
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel
import org.wit.daytripper.helpers.showImagePicker
import org.wit.daytripper.models.Location
import timber.log.Timber.i


class DayTripperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaytripperBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var dayTrip = DayTripperModel()
    var edit = false
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaytripperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.tripLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (dayTrip.zoom != 0f) {
                location.lat =  dayTrip.lat
                location.lng = dayTrip.lng
                location.zoom = dayTrip.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        app = application as MainApp
        i("Day Trip Activity started...")

        if (intent.hasExtra("daytrip_edit")) {
            edit = true
            dayTrip = intent.extras?.getParcelable("daytrip_edit")!!
            binding.dayTripTitle.setText(dayTrip.title)
            binding.description.setText(dayTrip.description)
            binding.ratingBar.setRating(dayTrip.rating.toFloat())
            binding.btnAdd.setText(R.string.save_daytrip)
            Picasso.get()
                .load(dayTrip.image)
                .into(binding.daytripImage)
            if (dayTrip.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_trip_image)
            }
        }


        //Add
        binding.btnAdd.setOnClickListener() {
            dayTrip.title = binding.dayTripTitle.text.toString()
            dayTrip.description = binding.description.text.toString()
            dayTrip.rating = binding.ratingBar.rating.toDouble()
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_daytrip, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_report -> { startActivity(Intent(this, DayTripListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            dayTrip.image = result.data!!.data!!
                            Picasso.get()
                                .load(dayTrip.image)
                                .into(binding.daytripImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            dayTrip.lat = location.lat
                            dayTrip.lng = location.lng
                            dayTrip.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}