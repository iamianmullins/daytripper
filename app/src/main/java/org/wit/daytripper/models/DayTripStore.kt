package org.wit.daytripper.models

import androidx.lifecycle.MutableLiveData

interface DayTripStore {
    fun findAll(dayTripList: MutableLiveData<List<DayTripperModel>>)
    fun findById(id: String) : DayTripperModel?
    fun create(daytrip: DayTripperModel)
    fun delete(id: String)
    fun deleteAll()

}