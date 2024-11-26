package ie.setu.utils

val driverClasses = setOf("Pro", "Pro-Am", "Am")

/**
 * Checks if the provided driver class is valid.
 * The valid driver classes are "Pro", "Pro-Am", and "Am", and the check is case-insensitive.
 *
 * @param classToCheck The driver class to validate.
 * @return `true` if the driver class is valid (one of "Pro", "Pro-Am", or "Am"),
 *         `false` if otherwise.
 */
fun isValidDriverClass(classToCheck: String?): Boolean {
    for (driverClass in driverClasses) {
        if (driverClass.equals(classToCheck, ignoreCase = true)) {
            return true
        }
    }
    return false
}
