package org.wit.daytripper.ui.daytrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.daytripper.models.DayTripManager
import org.wit.daytripper.models.DayTripperModel

class DayTripViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addDayTrip(dayTrip: DayTripperModel) {
        status.value = try {
            DayTripManager.create(dayTrip)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}

