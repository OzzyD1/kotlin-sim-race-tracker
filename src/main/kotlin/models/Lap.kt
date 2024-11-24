package ie.setu.models

data class Lap (
//Lap id also acts as lap number, just need to add 1 to user display
    var lapId: Int = 0,
//TODO: Write function to convert to time format
    var lapTime: Int,
    var pitTime: Int,
    var yellowFlag: Boolean,
    var redFlag: Boolean
)