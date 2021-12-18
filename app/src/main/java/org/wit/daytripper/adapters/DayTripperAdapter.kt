package org.wit.daytripper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.daytripper.R
import org.wit.daytripper.databinding.CardDaytripBinding
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber

interface DayTripListener {
    fun onDayTripClick(daytrip: DayTripperModel)
}

class DayTripperAdapter constructor(private var dayTrips: List<DayTripperModel>,
                                  private val listener: DayTripListener)
    : RecyclerView.Adapter<DayTripperAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDaytripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val dayTrip = dayTrips[holder.adapterPosition]
        holder.bind(dayTrip,listener)
    }

    override fun getItemCount(): Int = dayTrips.size

    inner class MainHolder(val binding : CardDaytripBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dayTrip: DayTripperModel, listener: DayTripListener) {
            //Timber.plant(Timber.DebugTree())
            //Timber.i("Day Trips" + dayTrip)

            binding.dayTrip = dayTrip

            //binding.imageIcon.setImageResource(R.drawable.lotr)
            //binding.description.text = "DayTrip Title: " +  dayTrip.title
            //binding.dayTripTitle.text = "Description: " +  dayTrip.description
            //binding.rating.text = "Rating: " +  dayTrip.rating.toString()
            //binding.lat.text = "Longitude: " +  dayTrip.lat.toString()
            //binding.lng.text = "Longitude: " + dayTrip.lng.toString()
            //binding.timest.text = "Created: " +  dayTrip.timest.toString()

            binding.root.setOnClickListener { listener.onDayTripClick(dayTrip) }
            binding.executePendingBindings()
        }
    }
}