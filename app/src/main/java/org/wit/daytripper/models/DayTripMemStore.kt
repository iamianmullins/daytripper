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

    override fun findAll(dayTripList: MutableLiveData<List<DayTripperModel>>) {

        val call = DayTripClient.getApi().findall()

        call.enqueue(object : Callback<List<DayTripperModel>> {
            override fun onResponse(call: Call<List<DayTripperModel>>,
                                    response: Response<List<DayTripperModel>>
            ) {
                dayTripList.value = response.body() as ArrayList<DayTripperModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DayTripperModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, dayTripList: MutableLiveData<List<DayTripperModel>>) {

        val call = DayTripClient.getApi().findall(email)

        call.enqueue(object : Callback<List<DayTripperModel>> {
            override fun onResponse(call: Call<List<DayTripperModel>>,
                                    response: Response<List<DayTripperModel>>
            ) {
                dayTripList.value = response.body() as ArrayList<DayTripperModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DayTripperModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, dayTrip: MutableLiveData<DayTripperModel>)   {

        val call = DayTripClient.getApi().get(email,id)

        call.enqueue(object : Callback<DayTripperModel> {
            override fun onResponse(call: Call<DayTripperModel>, response: Response<DayTripperModel>) {
                dayTrip.value = response.body() as DayTripperModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<DayTripperModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create(dayTrip: DayTripperModel) {
        val call = DayTripClient.getApi().post(dayTrip.email,dayTrip)

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
    }

    override fun delete(email: String,id: String) {
        val call = DayTripClient.getApi().delete(email,id)

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


    override fun update(email: String,id: String, dayTrip: DayTripperModel) {

        val call = DayTripClient.getApi().put(email,id,dayTrip)

        call.enqueue(object : Callback<DayTripWrapper> {
            override fun onResponse(call: Call<DayTripWrapper>,
                                    response: Response<DayTripWrapper>
            ) {
                val dayTripWrapper = response.body()
                if (dayTripWrapper != null) {
                    Timber.i("Retrofit Update ${dayTripWrapper.message}")
                    Timber.i("Retrofit Update ${dayTripWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DayTripWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }

}