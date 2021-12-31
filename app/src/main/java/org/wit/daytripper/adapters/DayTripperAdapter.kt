package org.wit.daytripper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.daytripper.R
import org.wit.daytripper.databinding.CardDaytripBinding
import org.wit.daytripper.helpers.customTransformation
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber

interface DayTripListener {
    fun onDayTripClick(daytrip: DayTripperModel)
}

class DayTripperAdapter constructor(private var dayTrips: ArrayList<DayTripperModel>,
                                  private val listener: DayTripListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<DayTripperAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDaytripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val dayTrip = dayTrips[holder.adapterPosition]
        holder.bind(dayTrip,listener)
    }

    fun removeAt(position: Int) {
        dayTrips.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = dayTrips.size

    inner class MainHolder(val binding : CardDaytripBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(dayTrip: DayTripperModel, listener: DayTripListener) {
            var num = dayTrip.rating
            Picasso.get().load(dayTrip.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.dayTrip = dayTrip
            binding.root.tag = dayTrip
            binding.imageIcon.setImageResource(R.drawable.lotr)
            binding.ratingBarView.numStars= num.toInt()
            binding.root.setOnClickListener { listener.onDayTripClick(dayTrip) }
            binding.executePendingBindings()
        }
    }
}
