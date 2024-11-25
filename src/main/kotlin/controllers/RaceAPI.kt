package ie.setu.controllers

import ie.setu.models.Race
import ie.setu.utils.formatListString
import persistence.Serializer


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
    fun listAllRaces(): String = if (races.isEmpty()) "No Races Stored" else formatListString(races)
    fun listCompletedRaces() = if (numberofCompletedRaces() == 0) "No Completed Races Stored" else formatListString(races.filter { race -> race.raceCompleted })
    fun listUncompletedRaces() = if (numberofUncompletedRaces() == 0) "No Uncompleted Races Stored" else formatListString(races.filter { race -> !race.raceCompleted })

//Counting
    fun numberOfRaces() = races.size
    fun numberofCompletedRaces(): Int = races.count { race -> race.raceCompleted }
    fun numberofUncompletedRaces(): Int = races.count { race -> !race.raceCompleted}

//Searching
    fun findRace(raceId: Int) = races.find { race -> race.raceID == raceId}
}