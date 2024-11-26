package ie.setu.models

import ie.setu.utils.formatSetString

/**
 * Represents a race with its details and associated laps.
 *
 * @property raceID The unique identifier for the race.
 * @property eventName The name of the event associated with the race.
 * @property raceTrack The track where the race takes place.
 * @property raceClass The class of the race (essentially skill level).
 * @property raceCompleted Indicates if the race is completed.
 * @property laps A set of laps associated with this race.
 */
data class Race(
    var raceID: Int = 0,
    var eventName: String,
    var raceTrack: String,
    var raceClass: String,
    var raceCompleted: Boolean,
    var laps: MutableSet<Lap> = mutableSetOf()){

//CRUD

    /**
     * Tracks the next available lap ID so that 1 is guaranteed start. Laps start at 1
     */
    private var lastLapId = 1

    /**
     * Generates the next unique ID for a new lap.
     *
     * @return The next lap ID as an integer.
     */
    private fun getNextLapId() = lastLapId++

    /**
     * Adds a new lap to the race.
     *
     * @param lap The [Lap] to be added.
     * @return `true` if the lap was successfully added, `false` otherwise.
     */
    fun addLap(lap: Lap): Boolean {
        lap.lapId = getNextLapId()
        return laps.add(lap)
    }

    /**
     * Deletes a lap from the race by its ID.
     *
     * @param id The ID of the lap to be deleted.
     * @return `true` if a lap was successfully deleted, `false` otherwise.
     */
    fun deleteLap(id: Int): Boolean = laps.removeIf { lap -> lap.lapId == id}

    /**
     * Finds a lap in the race by its ID.
     *
     * @param id The ID of the lap to search for.
     * @return The [Lap] with the specified ID or `null` if not found.
     */
    fun findOne(id: Int): Lap? = laps.find { lap -> lap.lapId == id }

    /**
     * Updates the details of a lap in the race.
     *
     * @param id The ID of the lap to update.
     * @param newLap The new [Lap] details to update.
     * @return `true` if the lap was successfully updated, `false` otherwise.
     */
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

    /**
     * Gets the total number of laps in the race.
     *
     * @return The number of laps as an integer.
     */
    fun numberOfLaps() = laps.size

    /**
     * Lists all laps in the race in a formatted string.
     *
     * @return A formatted string of laps or a message indicating no laps are present.
     */
    fun listLaps() = if (laps.isEmpty()) "No laps added to race" else formatSetString(laps)
}