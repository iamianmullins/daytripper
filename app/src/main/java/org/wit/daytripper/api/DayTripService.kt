package org.wit.daytripper.api


import org.wit.daytripper.models.DayTripperModel
import retrofit2.Call
import retrofit2.http.*

interface DayTripService {
    @GET("/donations")
    fun getall(): Call<List<DayTripperModel>>

    @GET("/donations/{id}")
    fun get(@Path("id") id: String): Call<DayTripperModel>

    @DELETE("/donations/{id}")
    fun delete(@Path("id") id: String): Call<DayTripWrapper>

    @POST("/donations")
    fun post(@Body dayTrip: DayTripperModel): Call<DayTripWrapper>

    @PUT("/donations/{id}")
    fun put(@Path("id") id: String,
            @Body dayTrip: DayTripperModel
    ): Call<DayTripWrapper>
}