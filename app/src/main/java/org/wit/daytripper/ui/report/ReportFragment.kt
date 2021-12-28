package org.wit.daytripper.ui.report

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.daytripper.R
import org.wit.daytripper.adapters.DayTripListener
import org.wit.daytripper.adapters.DayTripperAdapter
import org.wit.daytripper.databinding.FragmentReportBinding
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel
import org.wit.daytripper.utils.SwipeToDeleteCallback
import org.wit.daytripper.utils.createLoader
import org.wit.daytripper.utils.hideLoader
import org.wit.daytripper.utils.showLoader

class ReportFragment : Fragment(), DayTripListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var reportViewModel: ReportViewModel
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        showLoader(loader,"Downloading DayTrips")
        reportViewModel.observableDayTripsList.observe(viewLifecycleOwner, Observer {
                dayTrips ->
            dayTrips?.let {
                render(dayTrips as ArrayList<DayTripperModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToDayTripFragment()
            findNavController().navigate(action)
        }

        setSwipeRefresh()

        reportViewModel.load()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as DayTripperAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                reportViewModel.delete(viewHolder.itemView.tag as String)
                Toast.makeText(context,getString(R.string.delete_message), Toast.LENGTH_LONG).show()

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)


        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_report, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(dayTrips: ArrayList<DayTripperModel>) {
        fragBinding.recyclerView.adapter = DayTripperAdapter(dayTrips ,this)
        if (dayTrips.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.dayTripsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.dayTripsNotFound.visibility = View.GONE
        }
    }

    override fun onDayTripClick(dayTrip: DayTripperModel) {
        val action = ReportFragmentDirections.actionReportFragmentToDayTripDetailFragment(dayTrip._id)
        findNavController().navigate(action)
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

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading DayTrips")
            reportViewModel.load()

        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }
}