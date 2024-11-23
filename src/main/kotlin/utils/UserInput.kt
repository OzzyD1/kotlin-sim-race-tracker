package ie.setu.utils

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

fun readNextBoolean(prompt: String?): Boolean {
    while (true) {
        when (readNextLine(prompt)) {
            "1" -> return true
            "0" -> return false
            else -> println("Invalid input. Please enter 1 for True or 0 for False.")
        }
    }
}
