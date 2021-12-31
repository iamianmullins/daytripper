package org.wit.daytripper.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.daytripper.models.DayTripStore
import org.wit.daytripper.models.DayTripperModel
import timber.log.Timber

var database: DatabaseReference = FirebaseDatabase.getInstance().reference

object FirebaseDBManager : DayTripStore {
    override fun findAll(dayTripList: MutableLiveData<DayTripperModel>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, dayTripList: MutableLiveData<List<DayTripperModel>>) {
        database.child("user-daytrips").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase DayTrip error : ${error.message}")
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<DayTripperModel>()
                    val children = snapshot.children
                    children.forEach {
                        val dayTrip = it.getValue(DayTripperModel::class.java)
                        localList.add(dayTrip!!)
                    }
                    database.child("user-daytrips").child(userid)
                        .removeEventListener(this)
                    dayTripList.value = localList
                }
            })
    }

    override fun findById(userid: String, daytripid: String, daytrip: MutableLiveData<DayTripperModel>) {
        database.child("user-daytrips").child(userid)
            .child(daytripid).get().addOnSuccessListener {
                daytrip.value = it.getValue(DayTripperModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(
            firebaseUser: MutableLiveData<FirebaseUser>,
            dayTrip: DayTripperModel) {
        Timber.i("Firebase DB Reference : $database")
        val uid = firebaseUser.value!!.uid
        val key = database.child("daytrips").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        dayTrip.uid = key
        val dayTripValues = dayTrip.toMap()
        val childAdd = HashMap<String, Any>()

        childAdd["/daytrips/$key"] = dayTripValues
        childAdd["/user-daytrips/$uid/$key"] = dayTripValues
        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, daytripid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/daytrips/$daytripid"] = null
        childDelete["/user-daytrips/$userid/$daytripid"] = null
        database.updateChildren(childDelete)
    }

override fun update(userid: String, daytripid: String, daytrip: DayTripperModel) {

        val dayTripValues = daytrip.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["daytrips/$daytripid"] = dayTripValues
        childUpdate["user-daytrips/$userid/$daytripid"] = dayTripValues

        database.updateChildren(childUpdate)
    }

}