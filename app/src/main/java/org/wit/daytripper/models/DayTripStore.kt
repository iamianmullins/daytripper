package org.wit.daytripper.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface DayTripStore {
    fun findAll(dayTripList:
                MutableLiveData<List<DayTripperModel>>)
    fun findAll(userid:String,
                dayTripList:
                MutableLiveData<List<DayTripperModel>>)
    fun findById(userid:String, daytripid: String,
                 daytrip: MutableLiveData<DayTripperModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, dayTrip: DayTripperModel)
    fun delete(userid:String, daytripid: String)
    fun update(userid:String, daytripid: String, daytrip: DayTripperModel)

}