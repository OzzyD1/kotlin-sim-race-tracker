# Kotlin Sim Race Tracker Console App
This console app keeps track of your races and the details of the laps within the race, information including skill level, performance metrics, lap times, and race events like pit stops and flags.

## Key Features
- Race Management
  - Add, update, list, and delete race events
  - Categorize races by Pro, Pro-Am, or Am driver classes
  - Mark races as completed or uncompleted

- Lap Management
  - Add laps with detailed data, including lap times, pit times, and flag durations
  - Update or delete laps from specific races effortlessly
  - Lap times are calculated automatically

- Data Management
  - Save and load race data in JSON format for persistent storage
  - Basic CRUD for both data models
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

## Getting Started
### Prerequisites
- Ensure you have [Kotlin](https://kotlinlang.org/docs/getting-started.html) installed.
- Install a compatible IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/).

### Steps
1. Clone this repository:  
   ```bash
   git clone https://github.com/OzzyD1/kotlin-sim-race-tracker

2. Open project in your IDE

3. Build and run in your IDE

## Or Run Release in CLI
Download `kotlin-sim-race-tracker-1.0.jar` from releases
run `java -jar .\kotlin-sim-race-tracker-1.0.jar` from the directory where the .jar file is located

## Usage
- Add a Race: Follow the prompts to add a new event
- View Completed Races: Select option 2 in the Race Menu
- Save Data: Go to the Data Management Menu and select "Save races."

## Known Issues
- Input validation for lap times is still limited
