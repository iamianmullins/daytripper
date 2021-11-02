package org.wit.daytripper.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.daytripper.R
import org.wit.daytripper.databinding.ActivityDayTripListBinding
import org.wit.daytripper.databinding.CardDaytripBinding
import org.wit.daytripper.main.MainApp
import org.wit.daytripper.models.DayTripperModel

class DayTripListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityDayTripListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayTripListBinding.inflate(layoutInflater)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PlacemarkAdapter(app.dayTrips)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
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
}


class PlacemarkAdapter constructor(private var placemarks: List<DayTripperModel>) :
    RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDaytripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val binding : CardDaytripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placemark: DayTripperModel) {
            binding.dayTripTitle.text = placemark.title
            binding.description.text = placemark.description
        }
    }

}