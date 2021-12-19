package org.wit.daytripper.models

interface DayTripStore {
    fun findAll(): List<DayTripperModel>
    fun findById(id: Long) : DayTripperModel?
    fun create(daytrip: DayTripperModel)
    fun update(daytrip: DayTripperModel)
    fun delete(daytrip: DayTripperModel)
    fun deleteAll()

}