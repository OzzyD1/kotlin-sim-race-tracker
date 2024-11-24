package ie.setu.controllers

import ie.setu.models.Race

class RaceAPI {

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

//Counting
    fun numberOfRaces() = races.size

//Searching
    fun findRace(raceId: Int) = races.find { race -> race.raceID == raceId}

//TODO: This belongs in utils
    private fun formatListString(formattedRace: List<Race>): String =
        formattedRace.joinToString(separator = "\n") { race -> races.indexOf(race).toString() + ": " + race.toString() }
}