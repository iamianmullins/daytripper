package org.wit.daytripper.models

interface DayTripStore {
    fun findAll(): List<DayTripperModel>
    fun create(daytrip: DayTripperModel)
    fun update(daytrip: DayTripperModel)
    fun delete(daytrip: DayTripperModel)

}