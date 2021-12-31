package org.wit.daytripper.ui.daytrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.daytripper.firebase.FirebaseDBManager
import org.wit.daytripper.firebase.FirebaseImageManager
import org.wit.daytripper.models.DayTripperModel

class DayTripViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addDayTrip(firebaseUser: MutableLiveData<FirebaseUser>,
                    dayTrip: DayTripperModel) {
        status.value = try {
            //DayTripManager.create(dayTrip)
            dayTrip.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser,dayTrip)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}

