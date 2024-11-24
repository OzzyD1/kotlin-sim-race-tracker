# Kotlin Sim Race Tracker Console App
This console app is used to keep track of your races and the details of the laps within the race, information including skill level, performance metrics, lap times, and race events like pit stops and flags.

## Data Model - Race Event
    var raceID: Int = 0,
    var eventName: String,
    var raceTrack: String,
    var raceClass: String,
    var raceCompleted: Boolean

## Data Model - Race Lap
    var lapId: Int = 0,
    var lapTime: Int,
    var pitTime: Int,
    var yellowFlag: Boolean,
    var redFlag: Boolean