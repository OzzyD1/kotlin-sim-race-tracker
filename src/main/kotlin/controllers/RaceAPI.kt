package ie.setu.controllers

import ie.setu.models.Race

class RaceAPI {

    private var races = ArrayList<Race>()

    fun add(race: Race): Boolean{
        return races.add(race)
    }

    fun listAllRaces(): String = if (races.isEmpty()) "No Races Stored" else formatListString(races)

    fun numberOfRaces() = races.size

    private fun formatListString(formattedRace: List<Race>): String =
        formattedRace.joinToString(separator = "\n") { race -> races.indexOf(race).toString() + ": " + race.toString() }
}