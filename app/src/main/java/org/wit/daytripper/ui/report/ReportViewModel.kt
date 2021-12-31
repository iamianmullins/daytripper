package org.wit.daytripper.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.daytripper.firebase.FirebaseDBManager
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val dayTripsList = MutableLiveData<List<DayTripperModel>>()

    var readOnly = MutableLiveData(false)

    val observableDayTripsList: LiveData<List<DayTripperModel>>
        get() = dayTripsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        readOnly.value = false
        try {
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                dayTripsList)
            Timber.i("Retrofit Success : $dayTripsList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(dayTripsList)
            Timber.i("Report LoadAll Success : ${dayTripsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}