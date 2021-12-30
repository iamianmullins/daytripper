package org.wit.daytripper.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.daytripper.models.DayTripManager
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val dayTripsList = MutableLiveData<List<DayTripperModel>>()

    val observableDayTripsList: LiveData<List<DayTripperModel>>
        get() = dayTripsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            DayTripManager.findAll(liveFirebaseUser.value?.email!!, dayTripsList)
            Timber.i("Retrofit Success : $dayTripsList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun delete(email: String, id: String) {
        try {
            DayTripManager.delete(email,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}