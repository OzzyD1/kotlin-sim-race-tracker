package ie.setu.models

//Duration object is very useful
//https://kotlinlang.org/docs/time-measurement.html#get-string-representation
import kotlin.time.Duration

data class Lap (
//Starting lap at 1 because it makes sense in the context but will this have coding consequences?
    var lapId: Int = 1,
//TODO: Write function to convert to time format or rethink the entire strategy
    var lapTime: Duration,
    var pitTime: String,
    var yellowFlag: String,
    var redFlag: String
)