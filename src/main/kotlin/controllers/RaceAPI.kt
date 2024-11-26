package ie.setu.controllers

import ie.setu.models.Race
import persistence.Serializer
import kotlin.time.Duration

/**
 * This class manages a list of races and provides functionality for adding, updating, deleting,
 * and filtering races. It uses a [Serializer] to load and store the notes persistently.
 *
 * @property serializer A serializer instance for reading and writing the notes.
 * @constructor Initializes the RaceAPI with the specified [serializerType].
 */
class RaceAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    /**
     * Loads Race data from persistent storage
     */
    @Throws(Exception::class)
    fun load() {
        races = serializer.read() as ArrayList<Race>
    }

    /**
     * Stores Race data from persistent storage
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(races)
    }

    /**
     * A list to store all races.
     */
    private var races = ArrayList<Race>()



//CRUD

    /**
     * Tracks the next available race ID so that 0 is guaranteed start
     */
    private var nextRaceId = 0

    /**
     * Adds a new [Race] to the list.
     *
     * @param race The [Race] to be added.
     * @return `true` if the note was successfully added, `false` otherwise.
     */
    fun add(race: Race): Boolean{
        race.raceID = nextRaceId++
        return races.add(race)
    }

    /**
     * Updates the details of an existing race identified by its ID.
     *
     * @param id The ID of the race to be updated.
     * @param race The new details of the race.
     * @return `true` if the race was successfully updated, `false` otherwise.
     */
    fun update(id: Int, race: Race?): Boolean {
        val foundRace = findRace(id)
        if ((foundRace != null) && (race != null)) {
            foundRace.eventName = race.eventName
            foundRace.raceTrack = race.raceTrack
            foundRace.raceClass = race.raceClass
            foundRace.raceCompleted =race.raceCompleted
            return true
        }
        return false
    }

    /**
     * Deletes a race identified by its ID.
     *
     * @param id The ID of the race to be deleted.
     * @return `true` if a race was successfully deleted, `false` otherwise.
     */
    fun delete(id: Int) = races.removeIf {race -> race.raceID == id}

//Listing

    /**
     * Lists all races with their details. Includes the total race time calculated
     * from the lap times.
     *
     * @return A string representation of all races or a message if no races are stored.
     */
    fun listAllRaces(): String {
        return if (races.isEmpty()) {
            "No Races Stored"
        } else {
            races.joinToString(separator = "\n") { race ->
    //            https://discuss.kotlinlang.org/t/duration-could-support-sum-sumby/21377
    //            Since we have to perform calculations on the Duration object, which stores time, we use fold() to sum multiple laps inside a Race together correctly
    //            Duration.Zero starts at 0 time
                val totalRaceTime = race.laps.fold(Duration.ZERO) { total, lap -> total + lap.lapTime }
                """
                Race ID: ${race.raceID}
                Event Name: ${race.eventName}
                Track: ${race.raceTrack}
                Class: ${race.raceClass}
                Completed: ${if (race.raceCompleted) "Yes" else "No"}
                Total Race Time: ${
                    if (totalRaceTime == Duration.ZERO)
                        "No laps entered"
                    else
                        totalRaceTime
                }
                """.trimIndent()
            }
        }
    }

    /**
     * Lists all completed races with their details.
     *
     * @return A string representation of completed races or a message if none exist.
     */
    fun listCompletedRaces(): String {
        return if (numberofCompletedRaces() == 0) {
            "No Completed Races Stored"
        } else {
            races.filter { it.raceCompleted }.joinToString(separator = "\n") { race ->
                val totalRaceTime = race.laps.fold(Duration.ZERO) { total, lap -> total + lap.lapTime }
                """
                Race ID: ${race.raceID}
                Event Name: ${race.eventName}
                Track: ${race.raceTrack}
                Class: ${race.raceClass}
                Completed: ${if (race.raceCompleted) "Yes" else "No"}
                Total Race Time: ${
                    if (totalRaceTime == Duration.ZERO)
                        "No laps entered"
                    else
                        totalRaceTime
                }
                """.trimIndent()
            }
        }
    }

    /**
     * Lists all uncompleted races with their details.
     *
     * @return A string representation of uncompleted races or a message if none exist.
     */
    fun listUncompletedRaces(): String {
        return if (numberofUncompletedRaces() == 0) {
            "No Uncompleted Races Stored"
        } else {
            races.filter { !it.raceCompleted }.joinToString(separator = "\n") { race ->
                val totalRaceTime = race.laps.fold(Duration.ZERO) { total, lap -> total + lap.lapTime }
                """
                Race ID: ${race.raceID}
                Event Name: ${race.eventName}
                Track: ${race.raceTrack}
                Class: ${race.raceClass}
                Completed: ${if (race.raceCompleted) "Yes" else "No"}
                Total Race Time: ${
                    if (totalRaceTime == Duration.ZERO)
                        "No laps entered"
                    else
                        totalRaceTime
                }
                """.trimIndent()
            }
        }
    }

//Counting

    /**
     * Returns the total number of races.
     *
     * @return The total number of races stored.
     */
    fun numberOfRaces() = races.size

    /**
     * Returns the number of completed races.
     *
     * @return The count of completed races.
     */
    fun numberofCompletedRaces(): Int = races.count { race -> race.raceCompleted }

    /**
     * Returns the number of uncompleted races.
     *
     * @return The count of uncompleted races.
     */
    fun numberofUncompletedRaces(): Int = races.count { race -> !race.raceCompleted}

//Searching

    /**
     * Finds a race by its ID.
     *
     * @param raceId The ID of the race to search for.
     * @return The [Race] with the specified ID or `null` if not found.
     */
    fun findRace(raceId: Int) = races.find { race -> race.raceID == raceId}
}