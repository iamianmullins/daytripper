package org.wit.daytripper.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar

import org.wit.daytripper.R
import org.wit.daytripper.databinding.FragmentDaytripBinding

import org.wit.daytripper.helpers.getTime
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import timber.log.Timber.i


class DayTripFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentDaytripBinding? = null
    private val fragBinding get() = _fragBinding!!

    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDaytripBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_create)

        setButtonListener(fragBinding)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DayTripFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    fun setButtonListener(layout: FragmentDaytripBinding) {
        layout.btnAdd.setOnClickListener {
            val title = layout.dayTripTitle.text.toString()
            val description = layout.description.text.toString()
            val rating = layout.ratingBar.rating.toDouble()
            val timest = getTime()
            if (layout.dayTripTitle.length() < 2) {
                Snackbar.make(it, R.string.enter_daytrip_title, Snackbar.LENGTH_LONG)
                    .show()
            } else if (layout.description.length() < 2) {
                Snackbar.make(it, R.string.enter_daytrip_description, Snackbar.LENGTH_LONG)
                    .show()
            } else if (layout.ratingBar.rating.toDouble() < 0.5) {
                Snackbar.make(it, R.string.enter_daytrip_rating, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                app.dayTrips.create(
                    DayTripperModel(
                        title = title,
                        description = description,
                        image = Uri.EMPTY,
                        rating = rating,
                        timest = timest,
                        //Temporarily Hardcoded
                        lat = 404.404,
                        lng = 404.404
                    )
                )

            Snackbar.make(it, R.string.success_message, Snackbar.LENGTH_LONG)
                .show()
            }
     }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_daytrip, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

}