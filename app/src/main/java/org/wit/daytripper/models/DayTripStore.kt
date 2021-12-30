package org.wit.daytripper.models

import androidx.lifecycle.MutableLiveData

interface DayTripStore {
    fun findAll(dayTripList:
                MutableLiveData<List<DayTripperModel>>)
    fun findAll(email:String,
                dayTripList:
                MutableLiveData<List<DayTripperModel>>)
    fun findById(email:String, id: String,
                 dayTrip: MutableLiveData<DayTripperModel>)
    fun create(dayTrip: DayTripperModel)
    fun delete(email:String,id: String)
    fun update(email:String,id: String,dayTrip: DayTripperModel)

}