package controllers

import ie.setu.models.Race
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RaceAPITest {
    private var round9WatkinsGlen: Race? = null
    private var cupCarSwimming: Race? = null
    private var nasrOffSeason: Race? = null
    private var mozaChallenbge: Race? = null
    private var sarcoMonza: Race? = null

    @BeforeEach
    fun setup() {
        round9WatkinsGlen = Race(0,"Round 9 Watkins Glenn", "Watkins Glenn", "Pro", "True")
    }

    @AfterEach
    fun tearDown() {
        round9WatkinsGlen = null
    }
}

