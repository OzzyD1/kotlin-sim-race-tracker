package ie.setu.utils

import ie.setu.models.Lap
import ie.setu.models.Race

fun formatListString(formattedRace: List<Race>): String = formattedRace.joinToString(separator = "\n") { race -> "$race" }
fun formatSetString(formattedLap: Set<Lap>): String = formattedLap.joinToString(separator = "\n") { lap -> "\t$lap" }