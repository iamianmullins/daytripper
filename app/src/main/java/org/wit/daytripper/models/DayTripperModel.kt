package org.wit.daytripper.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class DayTripperModel(
    var uid: String? = "",
    var title: String = "N/A",
    var description: String = "N/A",
    var rating: Double = 0.00,
    var likes: Int = 0,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var timest: String = "",
    var region: String = "",
    var target: String = "",
    var profilepic: String = "",
    var email: String? = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "description" to description,
            "rating" to rating,
            "likes" to likes,
            "lat" to lat,
            "lng" to lng,
            "timest" to timest,
            "region" to region,
            "target" to target,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}