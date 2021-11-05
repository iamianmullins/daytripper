package org.wit.daytripper.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.daytripper.R
import org.wit.daytripper.adapters.DayTripListener
import org.wit.daytripper.adapters.DayTripperAdapter
import org.wit.daytripper.databinding.ActivityDayTripListBinding
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel

class DayTripListActivity : AppCompatActivity(), DayTripListener  {

    lateinit var app: MainApp
    private lateinit var binding: ActivityDayTripListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayTripListBinding.inflate(layoutInflater)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)

        app = application as MainApp
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = DayTripperAdapter(app.dayTrips.findAll(),this)

        loadDayTrips()
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, DayTripperActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDayTripClick(daytrip: DayTripperModel) {
        val launcherIntent = Intent(this, DayTripperActivity::class.java)
        launcherIntent.putExtra("daytrip_edit", daytrip)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadDayTrips() }
    }

    private fun loadDayTrips() {
        showDayTrips(app.dayTrips.findAll())
    }

    fun showDayTrips (daytrips: List<DayTripperModel>) {
        binding.recyclerView.adapter = DayTripperAdapter(daytrips, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}