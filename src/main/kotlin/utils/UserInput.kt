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
