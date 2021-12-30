package org.wit.daytripper.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wit.daytripper.databinding.DayTripDetailFragmentBinding
import org.wit.daytripper.ui.auth.LoggedInViewModel
import org.wit.daytripper.ui.report.ReportViewModel


class DayTripDetailFragment : Fragment() {

    private lateinit var detailViewModel: DayTripDetailViewModel
    private val args by navArgs<DayTripDetailFragmentArgs>()
    private var _fragBinding: DayTripDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val reportViewModel : ReportViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = DayTripDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.editDayTripButton.setOnClickListener {
            detailViewModel.updateDayTrip(loggedInViewModel.liveFirebaseUser.value?.email!!,
                args.dayTripId, fragBinding.daytripvm?.observableDayTrip!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteDayTripButton.setOnClickListener {
            reportViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.email!!,
                detailViewModel.observableDayTrip.value?._id!!)
            findNavController().navigateUp()
        }

        detailViewModel = ViewModelProvider(this).get(DayTripDetailViewModel::class.java)
        detailViewModel.observableDayTrip.observe(viewLifecycleOwner, Observer { render() })

        return root
    }

    private fun render() {
        fragBinding.daytripvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getDayTrip(loggedInViewModel.liveFirebaseUser.value?.email!!,
            args.dayTripId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}