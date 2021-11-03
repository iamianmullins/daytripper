package org.wit.daytripper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.daytripper.databinding.CardDaytripBinding
import org.wit.daytripper.models.DayTripperModel

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
            binding.dayTripTitle.text = dayTrip.title
            binding.description.text = dayTrip.description
            binding.rating.text = dayTrip.rating.toString()
            binding.root.setOnClickListener { listener.onDayTripClick(dayTrip) }
        }
    }
}