package ie.setu.controllers

import ie.setu.models.Race

class RaceAPI {

    private var races = ArrayList<Race>()

    fun add(race: Race): Boolean{
        return races.add(race)
    }

}