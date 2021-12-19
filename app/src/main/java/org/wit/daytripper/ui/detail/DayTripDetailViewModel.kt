package org.wit.daytripper.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.daytripper.models.DayTripManager
import org.wit.daytripper.models.DayTripperModel

class DayTripDetailViewModel : ViewModel() {
    private val dayTrip = MutableLiveData<DayTripperModel>()

    val observableDayTrip: LiveData<DayTripperModel>
        get() = dayTrip

    fun getDayTrip(id: Long) {
        dayTrip.value = DayTripManager.findById(id)
    }
}