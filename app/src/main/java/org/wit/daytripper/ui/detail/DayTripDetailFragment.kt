package org.wit.daytripper.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.wit.daytripper.databinding.DayTripDetailFragmentBinding



class DayTripDetailFragment : Fragment() {

    private lateinit var detailViewModel: DayTripDetailViewModel
    private val args by navArgs<DayTripDetailFragmentArgs>()
    private var _fragBinding: DayTripDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = DayTripDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(DayTripDetailViewModel::class.java)
        detailViewModel.observableDayTrip.observe(viewLifecycleOwner, Observer { render() })

        return root
    }

    private fun render() {
        fragBinding.editTitle.setText("Waterford Castle")
        fragBinding.editDescription.setText("Fantastic")
        fragBinding.daytripvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getDayTrip(args.dayTripId.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}