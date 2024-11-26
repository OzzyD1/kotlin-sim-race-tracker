package ie.setu.utils

/**
 * Reads a 'String' input from the user and returns it.
 *
 * @param prompt The prompt to display to the user before reading input.
 * @return The user input as a string.
 */
fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

/**
 * Reads an integer input from the user, prompting the user until a valid integer is entered.
 * Repeatedly prompts the user until a valid input is entered.
 *
 * @param prompt The prompt to display to the user before reading input.
 * @return The integer entered by the user.
 */
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

/**
 * Reads a boolean input from the user, accepting "1" for `true` and "0" for `false`.
 * Repeatedly prompts the user until a valid input is entered.
 *
 * @param prompt The prompt to display to the user before reading input.
 * @return `true` if the user enters "1", `false` if the user enters "0".
 */
fun readNextBoolean(prompt: String?): Boolean {
    while (true) {
        when (readNextLine(prompt)) {
            "1" -> return true
            "0" -> return false
            else -> println("Invalid input. Please enter 1 for True or 0 for False.")
        }
    }
}