package ie.setu.utils

val driverClasses = setOf("Pro", "Pro-Am", "Am")

fun isValidDriverClass(classToCheck: String?): Boolean {
    for (driverClass in driverClasses) {
        if (driverClass.equals(classToCheck, ignoreCase = true)) {
            return true
        }
    }
    return false
}