package org.wit.daytripper.models

import androidx.lifecycle.MutableLiveData
import org.wit.daytripper.api.DayTripClient
import org.wit.daytripper.api.DayTripWrapper
import org.wit.daytripper.helpers.getTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object DayTripManager: DayTripStore {

    val daytrips = ArrayList<DayTripperModel>()

    override fun findAll(dayTripList: MutableLiveData<List<DayTripperModel>>) {

        val call = DayTripClient.getApi().getall()

        call.enqueue(object : Callback<List<DayTripperModel>> {
            override fun onResponse(call: Call<List<DayTripperModel>>,
                                    response: Response<List<DayTripperModel>>
            ) {
                dayTripList.value = response.body() as ArrayList<DayTripperModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DayTripperModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:String) : DayTripperModel? {
        var foundDayTrip: DayTripperModel? = daytrips.find { it._id == id }
        return foundDayTrip
    }

    override fun create(dayTrip: DayTripperModel) {
        val call = DayTripClient.getApi().post(dayTrip)

        call.enqueue(object : Callback<DayTripWrapper> {
            override fun onResponse(call: Call<DayTripWrapper>,
                                    response: Response<DayTripWrapper>
            ) {
                val dayTripWrapper = response.body()
                if (dayTripWrapper != null) {
                    Timber.i("Retrofit ${dayTripWrapper.message}")
                    Timber.i("Retrofit ${dayTripWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DayTripWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
        logAll()
    }

    override fun delete(id: String) {
        val call = DayTripClient.getApi().delete(id)

        call.enqueue(object : Callback<DayTripWrapper> {
            override fun onResponse(call: Call<DayTripWrapper>,
                                    response: Response<DayTripWrapper>
            ) {
                val dayTripWrapper = response.body()
                if (dayTripWrapper != null) {
                    Timber.i("Retrofit Delete ${dayTripWrapper.message}")
                    Timber.i("Retrofit Delete ${dayTripWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DayTripWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }


    override fun deleteAll(){
        daytrips.clear()
    }


    fun logAll() {
        daytrips.forEach{ i("${it}") }
    }
}