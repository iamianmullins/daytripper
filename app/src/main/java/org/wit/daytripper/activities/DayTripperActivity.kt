package org.wit.daytripper.activities

import android.content.Intent
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
import timber.log.Timber.i


class DayTripperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaytripperBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
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
}