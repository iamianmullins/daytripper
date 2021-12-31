package org.wit.daytripper.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.daytripper.firebase.FirebaseDBManager
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber
import java.lang.Exception

class DayTripDetailViewModel : ViewModel() {
    private val dayTrip = MutableLiveData<DayTripperModel>()

    var observableDayTrip: LiveData<DayTripperModel>
        get() = dayTrip
        set(value) {dayTrip.value = value.value}

    fun getDayTrip(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, dayTrip)
            Timber.i("Detail getDayTrip() Success : ${dayTrip.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getDayTrip() Error : $e.message")
        }
    }

    fun updateDayTrip(userid:String, id: String,dayTrip: DayTripperModel) {
        try {
            FirebaseDBManager.update(userid, id, dayTrip)
            Timber.i("Detail update() Success : $dayTrip")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}