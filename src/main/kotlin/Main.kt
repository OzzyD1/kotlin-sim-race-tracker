package ie.setu

fun main() {

}

fun mainMenu(): Int {
    print(""" 
        > -------------------------------------------
         > |        SIM RACE TRACKER APP            |
         > ------------------------------------------
         > | RACE MENU                              |
         > |   1) Add a race                        |
         > |   2) List all races                    |
         > |   3) Update a race                     |
         > |   4) Delete a race                     |
         > |   5) Archive a race                    |
         > |   6) Search race (by desc)             |
         > ------------------------------------------
         > | LAP MENU                               | 
         > |   6) Add lap to a race                 |
         > |   7) Update lap contents on a race     |
         > |   8) Delete lap from a race            |
         > ------------------------------------------
         > |   20) Save races                       |
         > |   21) Load races                       |
         > ------------------------------------------
         > |   0) Exit                              |
         > ------------------------------------------
         >""".trimMargin(">"))
    return readNextInt(" > ==>>")
}