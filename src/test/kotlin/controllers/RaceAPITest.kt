package controllers

import ie.setu.controllers.RaceAPI
import ie.setu.models.Race
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class RaceAPITest {
    private var round9WatkinsGlen: Race? = null
    private var cupCarSwimming: Race? = null
    private var nasrOffSeason: Race? = null
    private var mozaChallenbge: Race? = null
    private var sarcoMonza: Race? = null

    private var populatedRaces: RaceAPI? = RaceAPI(XMLSerializer(File("races.xml")))
    private var emptyRaces: RaceAPI? = RaceAPI(XMLSerializer(File("empty-races.xml")))

    @BeforeEach
    fun setup() {
        round9WatkinsGlen = Race(0,"Round 9 Watkins Glenn", "Watkins Glenn", "Pro", true)
        cupCarSwimming = Race(0, "Cup Car Swimming", "Laguna Seca", "Am", false)
        nasrOffSeason = Race(1, "Nasr Off-Season", "Silverstone", "Pro-Am", true)
        mozaChallenbge = Race(2, "Moza Challenge", "Monza", "Pro", false)
        sarcoMonza = Race(3, "Sarco Monza", "Monza", "Pro-Am", true)

        populatedRaces!!.add(round9WatkinsGlen!!)
        populatedRaces!!.add(cupCarSwimming!!)
        populatedRaces!!.add(nasrOffSeason!!)
        populatedRaces!!.add(mozaChallenbge!!)
        populatedRaces!!.add(sarcoMonza!!)
    }

    @AfterEach
    fun tearDown() {
        round9WatkinsGlen = null
        cupCarSwimming = null
        nasrOffSeason = null
        mozaChallenbge = null
        sarcoMonza = null
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty races.XML file.
            val storingRaces = RaceAPI(XMLSerializer(File("races.xml")))
            storingRaces.store()

            //Loading the empty races.xml file into a new object
            val loadedRaces = RaceAPI(XMLSerializer(File("races.xml")))
            loadedRaces.load()

            //Comparing the source of the races (storingRaces) with the XML loaded races (loadedRaces)
            assertEquals(0, storingRaces.numberOfRaces())
            assertEquals(0, loadedRaces.numberOfRaces())
            assertEquals(storingRaces.numberOfRaces(), loadedRaces.numberOfRaces())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't lose data`() {
            // Storing 3 races to the races.XML file.
            val storingRaces = RaceAPI(XMLSerializer(File("races.xml")))
            storingRaces.add(nasrOffSeason!!)
            storingRaces.add(mozaChallenbge!!)
            storingRaces.add(sarcoMonza!!)
            storingRaces.store()

            //Loading races.xml into a different collection
            val loadedRaces = RaceAPI(XMLSerializer(File("races.xml")))
            loadedRaces.load()

            //Comparing the source of the races (storingRaces) with the XML loaded races (loadedRaces)
            assertEquals(3, storingRaces.numberOfRaces())
            assertEquals(3, loadedRaces.numberOfRaces())
            assertEquals(storingRaces.numberOfRaces(), loadedRaces.numberOfRaces())
            assertEquals(storingRaces.findRace(0), loadedRaces.findRace(0))
            assertEquals(storingRaces.findRace(1), loadedRaces.findRace(1))
            assertEquals(storingRaces.findRace(2), loadedRaces.findRace(2))
        }
    }
}

