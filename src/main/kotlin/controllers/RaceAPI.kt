package ie.setu.controllers

import ie.setu.models.Race
import ie.setu.utils.formatListString
import persistence.Serializer
import kotlin.time.Duration


class RaceAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        races = serializer.read() as ArrayList<Race>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(races)
    }

    private var races = ArrayList<Race>()

//CRUD
    fun add(race: Race): Boolean{
        return races.add(race)
    }

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

    fun delete(id: Int) = races.removeIf {race -> race.raceID == id}

//Listing
//    Depreciated but leaving here just in case for now as I have not done extensive testing
//    fun listAllRaces(): String = if (races.isEmpty()) "No Races Stored" else formatListString(races)
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
//  fun listCompletedRaces() = if (numberofCompletedRaces() == 0) "No Completed Races Stored" else formatListString(races.filter { race -> race.raceCompleted })
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
//  fun listUncompletedRaces() = if (numberofUncompletedRaces() == 0) "No Uncompleted Races Stored" else formatListString(races.filter { race -> !race.raceCompleted })
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
    fun numberOfRaces() = races.size
    fun numberofCompletedRaces(): Int = races.count { race -> race.raceCompleted }
    fun numberofUncompletedRaces(): Int = races.count { race -> !race.raceCompleted}

//Searching
    fun findRace(raceId: Int) = races.find { race -> race.raceID == raceId}
}