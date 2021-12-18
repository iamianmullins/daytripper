package org.wit.daytripper.models

import org.wit.daytripper.helpers.getTime
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object DayTripManager: DayTripStore {

    val daytrips = ArrayList<DayTripperModel>()

    override fun findAll(): List<DayTripperModel> {
        return daytrips
    }

    override fun create(dayTrip: DayTripperModel) {
        dayTrip.id = getId()
        daytrips.add(dayTrip)
        logAll()
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
            foundDayTrip.timest = getTime()
            logAll()
        }
    }

    override fun delete(dayTrip: DayTripperModel) {
        var foundDayTrip: DayTripperModel? = daytrips.find { p -> p.id == dayTrip.id }
        if (foundDayTrip != null) {
            daytrips.remove(foundDayTrip)
        }
    }


    override fun deleteAll(){
        daytrips.clear()
    }


    fun logAll() {
        daytrips.forEach{ i("${it}") }
    }
}