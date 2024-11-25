package ie.setu.models

data class Lap (
//Starting lap at 1 because it makes sense in the context but will this have coding consequences?
    var lapId: Int = 1,
//TODO: Write function to convert to time format or rethink the entire strategy
    var lapTime: String,
    var pitTime: String,
    var yellowFlag: String,
    var redFlag: String
)