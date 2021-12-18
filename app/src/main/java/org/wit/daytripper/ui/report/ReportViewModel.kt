package org.wit.daytripper.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.daytripper.models.DayTripManager
import org.wit.daytripper.models.DayTripperModel

class ReportViewModel : ViewModel() {

    private val dayTripsList = MutableLiveData<List<DayTripperModel>>()

    val observableDayTripsList: LiveData<List<DayTripperModel>>
        get() = dayTripsList

    init {
        load()
    }

    fun load() {
        dayTripsList.value = DayTripManager.findAll()
    }
}