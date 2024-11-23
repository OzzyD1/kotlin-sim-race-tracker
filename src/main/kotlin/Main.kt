package ie.setu

import ie.setu.controllers.RaceAPI
import ie.setu.models.Race
import ie.setu.utils.isValidDriverClass
import io.github.oshai.kotlinlogging.KotlinLogging
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine
import ie.setu.utils.readNextBoolean
import kotlin.system.exitProcess

private val raceAPI = RaceAPI()
private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "App started successfully!" }
    runMenu()
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            0 -> exitApp()
            1 -> addRace()
            2 -> listAllRaces()
            3 -> updateRace()
            else -> println("Invalid option")
        }
    } while (true)
}

fun mainMenu(): Int {
    print(""" 
        > -------------------------------------------
         > |        SIM RACE TRACKER APP            |
         > ------------------------------------------
         > | RACE MENU                              |
         > |   1) Add a race                        |
         > |   2) List all races                    |
         > |   3) Update a race                     |
         > |   4) Delete a race                     |
         > |   5) Archive a race                    |
         > |   6) Search race (by desc)             |
         > ------------------------------------------
         > | LAP MENU                               | 
         > |   7) Add lap to a race                 |
         > |   8) Update lap contents on a race     |
         > |   9) Delete lap from a race            |A
         > ------------------------------------------
         > |   20) Save races                       |
         > |   21) Load races                       |
         > ------------------------------------------
         > |   0) Exit                              |
         > ------------------------------------------
         >""".trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun addRace(){
    logger.info { "addRace() invoked" }

    val eventName = readNextLine("Enter event name: ")
    val raceTrack = readNextLine("Enter event track: ")

    var raceClass: String
    do {
        raceClass = readNextLine("Enter your driver class (Pro, Pro-Am or Am): ")
    } while (!isValidDriverClass(raceClass))

    val raceCompleted = readNextBoolean("Was the race completed? (1: True or 0: False): ")

    val isAdded = raceAPI.add(Race(eventName = eventName, raceTrack = raceTrack, raceClass = raceClass, raceCompleted = raceCompleted))
    if (isAdded) { println("$eventName added successfully") } else { println("Add Unsuccessful") }
}

fun listAllRaces() {
    println(raceAPI.listAllRaces())
}

//TODO: This will display races with different statuses later
//fun listRaces() {
//    logger.info { "listRaces() invoked" }
//
//    if (raceAPI.numberOfRaces() > 0){
//
//    } else { println("No events to display") }

fun updateRace() {
    listAllRaces()
    if (raceAPI.numberOfRaces() > 0) {
        val id = readNextInt("Enter race number to update")
        if (raceAPI.findRace(id) != null) {
            val eventName = readNextLine("Enter event name: ")
            val raceTrack = readNextLine("Enter event track: ")

            var raceClass: String
            do {
                raceClass = readNextLine("Enter your driver class (Pro, Pro-Am or Am): ")
            } while (!isValidDriverClass(raceClass))

            val raceCompleted = readNextBoolean("Was the race completed? (1: True or 0: False): ")

            if (raceAPI.update(id, Race(id, eventName, raceTrack, raceClass, raceCompleted))){
                println("Update Successful")
            } else {
                println("Update Failed")
                }
        }
        else {
            println("Race number not found")
        }
    }
}

fun exitApp(){
    println("Exiting App")
    exitProcess(0)
}