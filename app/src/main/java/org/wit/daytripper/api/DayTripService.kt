package org.wit.daytripper.api


import org.wit.daytripper.models.DayTripperModel
import retrofit2.Call
import retrofit2.http.*

interface DayTripService {
    @GET("/donations")
    fun findall(): Call<List<DayTripperModel>>

    @GET("/donations/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<DayTripperModel>>

    @GET("/donations/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<DayTripperModel>

    @DELETE("/donations/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<DayTripWrapper>

    @POST("/donations/{email}")
    fun post(@Path("email") email: String?,
             @Body donation: DayTripperModel)
            : Call<DayTripWrapper>

    @PUT("/donations/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body donation: DayTripperModel
    ): Call<DayTripWrapper>
}
