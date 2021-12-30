package org.wit.daytripper.models

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DayTripperModel(
    var _id:String = "N/A",
    @SerializedName("paymenttype")
    var title: String = "",
    @SerializedName("message")
    var description: String = "",
    var image: Uri = Uri.EMPTY,
    @SerializedName("amount")
    var rating: Double = 0.00,
    var likes: Int = 0,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var timest: String = "",
    var email: String = "joe@bloggs.com") : Parcelable