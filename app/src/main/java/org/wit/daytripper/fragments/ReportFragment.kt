package org.wit.daytripper.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.daytripper.R
import org.wit.daytripper.adapters.DayTripperAdapter
import org.wit.daytripper.databinding.FragmentReportBinding
import org.wit.daytripper.main.MainApp
import timber.log.Timber

class ReportFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_report)

        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = DayTripperAdapter(app.dayTrips.findAll())

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_report, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.titleCondensed == "titleCondensed_deleteall") {
            var count = dayTripCount()
            app.dayTrips.deleteAll()
            var msg =""
            if (count == 1) {
                msg = "You have deleted  $count DayTrip"
            } else {
                msg = "You have deleted  $count DayTrips"
            }
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context, msg, duration).show()
        }
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun dayTripCount(): Int {
        val dayTripSize = app.dayTrips.findAll()
        return dayTripSize.size
    }

}