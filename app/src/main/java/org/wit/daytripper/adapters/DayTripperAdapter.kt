package org.wit.daytripper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.daytripper.R
import org.wit.daytripper.databinding.CardDaytripBinding
import org.wit.daytripper.models.DayTripperModel
import kotlin.math.roundToLong

interface DayTripListener {
    fun onDayTripClick(daytrip: DayTripperModel)
}

class DayTripperAdapter constructor(private var daytrips: List<DayTripperModel>,
                                    private val listener: DayTripListener) :
    RecyclerView.Adapter<DayTripperAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDaytripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val daytrip = daytrips[holder.adapterPosition]
        holder.bind(daytrip, listener)
    }

    override fun getItemCount(): Int = daytrips.size

    class MainHolder(private val binding : CardDaytripBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dayTrip: DayTripperModel, listener: DayTripListener) {
            binding.dayTripTitle.text = "Title: " + dayTrip.title
            binding.description.text = "Description: " + dayTrip.description
            binding.rating.text = "Rating: " + dayTrip.rating.toString()
            binding.lat.text = "Lat: " + String.format("%.5f", dayTrip.lat)
            binding.lng.text = "Long: " + String.format("%.5f", dayTrip.lng)
            Picasso.get().load(dayTrip.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onDayTripClick(dayTrip) }
        }
    }
}