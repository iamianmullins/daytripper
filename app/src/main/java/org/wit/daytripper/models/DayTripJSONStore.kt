package org.wit.daytripper.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.daytripper.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "daytrips.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<DayTripperModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class DayTripJSONStore(private val context: Context) : DayTripStore {

    var daytrips = mutableListOf<DayTripperModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<DayTripperModel> {
        logAll()
        return daytrips
    }

    override fun create(daytrip: DayTripperModel) {
        daytrip.id = generateRandomId()
        daytrips.add(daytrip)
        serialize()
    }

    override fun update(dayTrip: DayTripperModel) {
        var foundDayTrip: DayTripperModel? = daytrips.find { p -> p.id == dayTrip.id }
        if (foundDayTrip != null) {
            foundDayTrip.title = dayTrip.title
            foundDayTrip.description = dayTrip.description
            foundDayTrip.rating = dayTrip.rating
            foundDayTrip.image = dayTrip.image
            foundDayTrip.lat = dayTrip.lat
            foundDayTrip.lng = dayTrip.lng
            foundDayTrip.zoom = dayTrip.zoom
        }
        serialize()
    }

    override fun delete(dayTrip: DayTripperModel) {
        var foundDayTrip: DayTripperModel? = daytrips.find { p -> p.id == dayTrip.id }
        if (foundDayTrip != null) {
            daytrips.remove(foundDayTrip)
            serialize()
        }
    }

    override fun deleteAll() {
        daytrips.clear()
        serialize()
    }
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(daytrips, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        daytrips = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        daytrips.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}