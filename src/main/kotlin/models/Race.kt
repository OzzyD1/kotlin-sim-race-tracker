package ie.setu.models

import ie.setu.utils.formatSetString

data class Race(
    var raceID: Int = 0,
    var eventName: String,
    var raceTrack: String,
    var raceClass: String,
    var raceCompleted: Boolean,
    var laps: MutableSet<Lap> = mutableSetOf()){

//CRUD
    private var lastLapId = 0
    private fun getNextLapId() = lastLapId++
    fun addLap(lap: Lap): Boolean {
        lap.lapId = getNextLapId()
        return laps.add(lap)
    }

    fun deleteLap(id: Int): Boolean {
        return laps.removeIf { lap -> lap.lapId == id}
    }

    fun findOne(id: Int): Lap? {
        return laps.find { lap -> lap.lapId == id }
    }

    fun updateLap(id: Int, newLap: Lap): Boolean {
        val foundLap = findOne(id)
        if (foundLap != null) {
            foundLap.lapTime = newLap.lapTime
            foundLap.pitTime = newLap.pitTime
            foundLap.yellowFlag = newLap.yellowFlag
            foundLap.redFlag = newLap.redFlag
            return true
        }
        return false
    }

    fun numberOfLaps() = laps.size
    fun listLaps() = if (laps.isEmpty()) "No laps added to race" else formatSetString(laps)
}