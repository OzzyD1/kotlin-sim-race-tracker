package ie.setu

import ie.setu.controllers.RaceAPI
import ie.setu.models.Lap
import ie.setu.models.Race
import ie.setu.utils.isValidDriverClass
//import io.github.oshai.kotlinlogging.KotlinLogging
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine
import ie.setu.utils.readNextBoolean
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.system.exitProcess
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private val raceAPI = RaceAPI(JSONSerializer(File("races.json")))
//private val logger = KotlinLogging.logger {}

/**
 * Main entry point of the app.
 * Starts the [runMenu] menu loop.
 */
fun main() {
    runMenu()
}

/**
 * Runs the [mainMenu] main menu loop.
 */
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

/**
 * Displays the main menu and prompts the user for an option, the options lead to management menus
 *
 * @return the user's chosen option as an integer.
 */
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

//RACE MANAGEMENT
/**
 * Displays the Race Management Menu which allows the user to:
 * - Add a race [addRace]
 * - List Races (which leads to another menu) [listRaces]
 * - Update a race [updateRace]
 * - Delete a race [deleteRace]
 * - Return to root menu [runMenu]
 * */
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
             > ------------------------------------------
             > |   9) Return                            |
             > ------------------------------------------
             > MAIN MENU/RACE MENU ==>> """.trimMargin(">")
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

/**
 * Adds a new race to the system.
 * Prompts the user for race details, including:
 * - Event name
 * - Race track
 * - Driver class (validated using [isValidDriverClass] as Pro, Pro-Am, or Am)
 * - Completion status
 */
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

/**
 * Lists all races.
 */
fun listAllRaces() = println(raceAPI.listAllRaces())

/**
 * Lists all completed races.
 */
fun listCompletedRaces() = println(raceAPI.listCompletedRaces())

/**
 * Lists all uncompleted races.
 */
fun listUncompletedRaces() = println(raceAPI.listUncompletedRaces())

/**
 * Displays a menu for listing races and allows the user to filter:
 * - All races [listAllRaces]
 * - Completed races [listCompletedRaces]
 * - Uncompleted races [listUncompletedRaces]
 * If no race is found, an error is displayed
 */
fun listRaces() {
    if (raceAPI.numberOfRaces() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Races          |
                  > |   2) View COMPLETED Races    |
                  > |   3) View UNCOMPLETED Races  |
                  > --------------------------------
                  > MAIN MENU/RACE MENU/RACE LIST ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllRaces()
            2 -> listCompletedRaces()
            3 -> listUncompletedRaces()
            else -> println("Invalid option entered: $option")
        }
    } else { println("No events to display") }
}

/**
 * Updates an existing race
 * Prompts the user to select a race by ID, then enter new details for:
 * - Event name
 * - Race track
 * - Driver class (validated using [isValidDriverClass] as Pro, Pro-Am, or Am)
 * - Completion status
 * If the race ID is not found, an error message is displayed.
 */
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

/**
 * Deletes an existing race.
 * Prompts the user to select a race by ID for deletion.
 * Displays success or failure messages based.
 */
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
/**
 * Displays the Lap Management Menu which allows the user to:
 * - Add a lap to a race [addLapToRace]
 * - Update lap details in a race [updateLapInRace]
 * - Delete a lap from a race [deleteLapinRace]
 * - Return to root menu [runMenu]
 */
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
             > MAIN MENU/LAP MENU ==>> """.trimMargin(">")
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

/**
 * Adds a new lap to an existing race.
 * Prompts the user to select a race [raceIdPrompt], then enter lap details [readLapTime]:
 * - Lap time (Stored in a 'Duration' object)
 * - Pit time (in SSmm format)
 * - Yellow flag duration (in MMSSmm format)
 * - Red flag duration (in MMSSmm format)
 * Validates race and lap selections and provides feedback on success or failure.
 */
private fun addLapToRace() {
//  TODO: Displays Kotlin.Unit - this is unintentional
    print(listAllRaces())
    val race = raceIdPrompt()
    if (race != null) {
//      TODO: Add validation for time inputs
        val lapTime = readLapTime()
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

/**
 * Updates an existing lap in a race.
 * Prompts the user to:
 * - Select a race by its ID [raceIdPrompt]
 * - View and select a lap to update by its ID
 * - Enter new details for the selected lap [readLapTime]
 * Validates race and lap selections and provides feedback on success or failure.
 */
//TODO: Get caught in a loop if there is no laps in a race (Pressing 9 works?)
fun updateLapInRace() {
    print(listAllRaces())
    val race = raceIdPrompt()
    if (race != null) {
        print(race.listLaps())
        val id = race.findOne(readNextInt("\nEnter Lap to Update: "))
        if (id != null) {
//          TODO: Add validation
            val lapTime = readLapTime()
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

/**
 * Deletes an existing lap from a race.
 * Prompts the user to:
 * - Select a race by its ID [raceIdPrompt]
 * - View and select a lap to delete by its ID
 * Validates race and lap selections and provides feedback on success or failure.
 */
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
                println("Delete Unsuccessful")
            }
        } else {
            println("Lap not found")
        }
    } else {
        println("Race not found")
    }
}

//DATA MANAGEMENT
/**
 * Displays the Data Management Menu and handles options for:
 * - Saving race data to a JSON file [save]
 * - Loading race data from a JSON file [load]
 */
fun dataManagementMenu() {
    do {
        val option = readNextInt(
            """
            > -------------------------------------------
             > |        DATA MENU                       |
             > ------------------------------------------
             > |   1) Save races  (JSON)                |
             > |   2) Load races  (JSON)                |
             > ------------------------------------------
             > |   9) Return                            |
             > ------------------------------------------
             > MAIN MENU/DATA MENU ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> save()
            2 -> load()
            9 -> runMenu()
            else -> println("Invalid Option")
        }
    } while (true)
}

/**
 * Saves the current race data to a JSON file.
 */
fun save() {
    try {
        raceAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Loads the current race data to a JSON file.
 */
fun load() {
    try {
        raceAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Exits and terminated the app.
 */
fun exitApp(){
    println("Exiting App")
    exitProcess(0)
}

//Helper Functions
/**
 * Prompts the user to input a race ID and fetches the corresponding race information from the race API.
 *
 * @return The race information retrieved from the API using the provided race ID.
 */
fun raceIdPrompt() = raceAPI.findRace(readNextInt("\nEnter Race: "))

/**
 * Prompts the user to input the time for a lap in minutes, seconds, and milliseconds,
 * and returns the total lap time as a `Duration` object.
 *
 * @return A `Duration` object representing the lap time, calculated from the user input.
 */
fun readLapTime(): Duration {
    val lapMins = readNextInt("Lap Minutes: ").minutes
    val lapSecs = readNextInt("Lap Seconds: ").seconds
    val lapMilSecs = readNextInt("Lap Milliseconds: ").milliseconds
    return lapMins + lapSecs + lapMilSecs
}

