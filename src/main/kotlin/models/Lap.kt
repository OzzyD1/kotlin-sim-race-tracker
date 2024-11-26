package ie.setu.models

//https://kotlinlang.org/docs/time-measurement.html#get-string-representation
import kotlin.time.Duration

/**
 * Represents a single lap in a race, including timing and flag details.
 *
 * @property lapId The unique identifier for the lap. Defaults to 1, as laps in racing typically start at 1.
 * @property lapTime The duration of the lap, represented using [Duration].
 * @property pitTime A string representing the time spent in the pit stop during this lap.
 * @property yellowFlag A string indicating if a yellow flag was issued during the lap. Typically "Yes" or "No".
 * @property redFlag A string indicating if a red flag was issued during the lap. Typically "Yes" or "No".
 */
data class Lap (
//Starting lap at 1 because it makes sense in the context but will this have coding consequences?
    var lapId: Int = 1,
    var lapTime: Duration,
    var pitTime: String,
    var yellowFlag: String,
    var redFlag: String
)