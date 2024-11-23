package ie.setu

import io.github.oshai.kotlinlogging.KotlinLogging
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "App started successfully!" }
    mainMenu()
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            0 -> exitProcess(0)
            1 -> addRace()
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
         > |   9) Delete lap from a race            |
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
    //TODO: There should only be 3 options below
    val raceClass = readNextLine("Enter your driver class (Pro, Pro-Am or Am): ")
    //TODO: Need to make below boolean
    val raceCompleted = readNextLine("Was race completed: ")
}

fun exit(){
    println("Exiting App")
    exitProcess(0)
}