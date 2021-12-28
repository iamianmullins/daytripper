package org.wit.daytripper.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.daytripper.models.DayTripManager
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val dayTripsList = MutableLiveData<List<DayTripperModel>>()

    val observableDayTripsList: LiveData<List<DayTripperModel>>
        get() = dayTripsList

    init {
        load()
    }

    fun load() {
        try {
            DayTripManager.findAll(dayTripsList)
            Timber.i("Retrofit Success : $dayTripsList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun delete(id: String) {
        try {
            DayTripManager.delete(id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: java.lang.Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}