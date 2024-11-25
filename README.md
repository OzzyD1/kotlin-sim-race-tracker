# Kotlin Sim Race Tracker Console App
This console app is used to keep track of your races and the details of the laps within the race, information including skill level, performance metrics, lap times, and race events like pit stops and flags.

Basic CRUD for both models
## Data Model - Race Event
    var raceID: Int,
    var eventName: String,
    var raceTrack: String,
    var raceClass: String,
    var raceCompleted: Boolean

## Data Model - Race Lap
    var lapId: Int, 
    var lapTime: Duration,
    var pitTime: Int,
    var yellowFlag: String,
    var redFlag: String

## Data Persistence
Currently, the data can be saved in JSON format to be loaded after the app has been closed.