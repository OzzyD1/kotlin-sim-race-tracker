package ie.setu

import ie.setu.controllers.RaceAPI
import ie.setu.models.Lap
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
    runMenu()
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            0 -> exitApp()
            1 -> raceManagementMenu()
            2 -> lapManagementMenu()
            3 -> dataManagementMenu()
            else -> println("Invalid option: $option")
        }
    } while (true)
}

fun mainMenu(): Int {
    print(""" 
        > -------------------------------------------
         > |        SIM RACE TRACKER APP            |
         > ------------------------------------------
         > |   1) Race Menu >                       | 
         > |   2) Lap Menu >                        |
         > |   3) Data Menu >                       |
         > |   0) Exit                              |
         > ------------------------------------------
         >""".trimMargin(">"))
    return readNextInt(" MAIN MENU ==>> ")
}

//RACE MANAGEMENT (CRUD)
fun raceManagementMenu() {
    do {
        val option = readNextInt("""
            > -------------------------------------------
             > |        RACE MENU                       |
             > ------------------------------------------
             > |   1) Add a race                        |
             > |   2) List Races >                      |
             > |   3) Update a race                     |
             > |   4) Delete a race                     |
             > |   NA) Search race (by desc)            |
             > ------------------------------------------
             > |   9) Return                            |
             > ------------------------------------------
             > RACE MENU ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> addRace()
            2 -> listRaces()
            3 -> updateRace()
            4 -> deleteRace()
            9 -> runMenu()
            else -> println("Invalid Option")
        }
    } while (true)
}

fun addRace(){
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

fun listAllRaces() = println(raceAPI.listAllRaces())
fun listCompletedRaces() = println(raceAPI.listCompletedRaces())
fun listUncompletedRaces() = println(raceAPI.listUncompletedRaces())

fun listRaces() {
    if (raceAPI.numberOfRaces() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Races          |
                  > |   2) View COMPLETED Races    |
                  > |   3) View UNCOMPLETED Races  |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllRaces()
            2 -> listCompletedRaces()
            3 -> listUncompletedRaces()
            else -> println("Invalid option entered: $option")
        }
    } else { println("No events to display") }
}

fun updateRace() {
    listAllRaces()
    if (raceAPI.numberOfRaces() > 0) {
        val id = readNextInt("Enter race number to update: ")
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

fun deleteRace() {
    listAllRaces()
    if (raceAPI.numberOfRaces() > 0) {
        val iD = readNextInt("Enter ID of race to delete: ")
        val raceToDelete = raceAPI.delete(iD)
        if (raceToDelete){
            println("$iD Deleted Successfully")
        } else {
            println("Delete Unsuccessful")
        }
    }
}

//LAP MANAGEMENT
fun lapManagementMenu() {
    do {
        val option = readNextInt(
            """
            > -------------------------------------------
             > |        LAP MENU                        |
             > ------------------------------------------
             > |   1) Add lap to a race                 |
             > |   2) Update lap contents on a race     |
             > |   3) Delete lap from a race           |
             > ------------------------------------------
             > |   9) Return                            |
             > ------------------------------------------
             > LAP MENU ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> addLapToRace()
            2 -> updateLapInRace()
            3 -> deleteLapinRace()
            9 -> runMenu()
            else -> println("Invalid Option")
        }
    } while (true)
}

private fun addLapToRace() {
//  TODO: Displays Kotlin.Unit - this is unintentional - why here and not up there?
    print(listAllRaces())
    val race = raceIdPrompt()
    if (race != null) {
//      TODO: Add validation
        val lapTime = readNextLine("Lap Time (MMSSmm): ")
        val pitTime = readNextLine("Pit Time (SSmm): ")
        val yellowFlag = readNextLine("Yellow Flag Duration (MMSSmm): ")
        val redFlag = readNextLine("Red Flag Duration (MMSSmm): ")

        val isAdded = race.addLap(Lap(lapTime = lapTime, pitTime = pitTime, yellowFlag = yellowFlag, redFlag = redFlag))
        if (isAdded) {
            println("Add Successful")
        } else {
            println("Add Unsuccessful")
        }
    } else {
        println("Race not found.")
    }
}

//TODO: Get caught in a loop if there is no laps in a race (Pressing 9 works?)
fun updateLapInRace() {
    print(listAllRaces())
    val race = raceIdPrompt()
    if (race != null) {
        print(race.listLaps())
        val id = race.findOne(readNextInt("\nEnter Lap to Update: "))
        if (id != null) {
//          TODO: Add validation
            val lapTime = readNextLine("Lap Time (MMSSmm): ")
            val pitTime = readNextLine("Pit Time (SSmm): ")
            val yellowFlag = readNextLine("Yellow Flag Duration (MMSSmm): ")
            val redFlag = readNextLine("Red Flag Duration (MMSSmm): ")

            if (race.updateLap(id.lapId, Lap(lapId = id.lapId, lapTime = lapTime, pitTime = pitTime, yellowFlag = yellowFlag, redFlag = redFlag))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("Lap not found")
        }
    } else {
        println("Race not found")
    }
}

fun deleteLapinRace() {
    print(listAllRaces())
    val race = raceIdPrompt()
    if (race != null) {
        print(race.listLaps())
        val id = race.findOne(readNextInt("\nEnter Lap to Delete: "))
        if (id != null) {
            if (race.deleteLap(id.lapId)) {
                println("Delete Successful")
            } else {
                println("Delete Unuccessful")
            }
        } else {
            println("Lap not found")
        }
    } else {
        println("Race not found")
    }
}

//DATA MANAGEMENT
fun dataManagementMenu() {
    do {
        val option = readNextInt(
            """
            > -------------------------------------------
             > |        DATA MENU                       |
             > ------------------------------------------
             > |   NA) Save races                       |
             > |   NA) Load races                       |
             > ------------------------------------------
             > |   9) Return                            |
             > ------------------------------------------
             > DATA MENU ==>> """.trimMargin(">")
        )

        when (option) {
            9 -> runMenu()
            else -> println("Invalid Option")
        }
    } while (true)
}

fun exitApp(){
    println("Exiting App")
    exitProcess(0)
}

//Helper Functions
fun raceIdPrompt() = raceAPI.findRace(readNextInt("\nEnter Race: "))
