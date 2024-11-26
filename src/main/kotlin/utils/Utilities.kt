package ie.setu.utils

import ie.setu.models.Lap

/**
 * Formats a set of laps into a string representation, with each lap on a new line.
 * Each lap is prefixed with a tab character.
 *
 * @param formattedLap A set of [Lap] objects to be formatted.
 * @return A string where each lap is represented on a new line, with each line starting with a tab character.
 */
fun formatSetString(formattedLap: Set<Lap>): String = formattedLap.joinToString(separator = "\n") { lap -> "\t$lap" }