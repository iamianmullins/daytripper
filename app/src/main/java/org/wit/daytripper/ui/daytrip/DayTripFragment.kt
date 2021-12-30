package org.wit.daytripper.ui.daytrip

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar

import org.wit.daytripper.R
import org.wit.daytripper.databinding.FragmentDaytripBinding

import org.wit.daytripper.helpers.getTime
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel
import org.wit.daytripper.ui.auth.LoggedInViewModel
import org.wit.daytripper.ui.report.ReportViewModel

class DayTripFragment : Fragment() {

    private var _fragBinding: FragmentDaytripBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var dayTripViewModel: DayTripViewModel
    private val reportViewModel: ReportViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDaytripBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        dayTripViewModel = ViewModelProvider(this).get(DayTripViewModel::class.java)
        dayTripViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        activity?.title = getString(R.string.action_create)

        setButtonListener(fragBinding)
        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.dayTripError), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DayTripFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: FragmentDaytripBinding) {
        layout.btnAdd.setOnClickListener {
            val title = layout.dayTripTitle.text.toString()
            val description = layout.description.text.toString()
            val rating = layout.ratingBar.rating.toDouble()
            val likes = 0
            val timest = getTime()
            val lat = 404.40400
            val lng = 404.40400
            if (layout.dayTripTitle.length() < 2) {
                Snackbar.make(it, R.string.enter_daytrip_title, Snackbar.LENGTH_LONG)
                    .show()
            } else if (layout.description.length() < 2) {
                Snackbar.make(it, R.string.enter_daytrip_description, Snackbar.LENGTH_LONG)
                    .show()
            } else if (layout.ratingBar.rating.toDouble() < 0.5) {
                Snackbar.make(it, R.string.enter_daytrip_rating, Snackbar.LENGTH_LONG)
                    .show()
            }
            dayTripViewModel.addDayTrip(DayTripperModel(
                title = title,
                description = description,
                rating = rating,
                likes = likes,
                timest = timest,
                lat = lat,
                lng = lng,
                email = loggedInViewModel.liveFirebaseUser.value?.email!!))
            Snackbar.make(it, R.string.success_message, Snackbar.LENGTH_LONG)
                .show()

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

    override fun onResume() {
        super.onResume()
        val reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        reportViewModel.observableDayTripsList.observe(viewLifecycleOwner, Observer {
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}