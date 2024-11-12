package ie.setu

fun main() {

}

fun mainMenu(): Int {
    print(""" 
        > ----------------------------------
         > |        SIM RACE TRACKER APP    |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List all notes            |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > |   5) Archive a note            |
         > |   6) Search note (by desc)     |
         > ----------------------------------
         > |   20) Save notes               |
         > |   21) Load notes               |
         > ----------------------------------
         > |   0) Exit                      |
         > ---------------------------------- 
         >""".trimMargin(">"))
    return readNextInt(" > ==>>")
}