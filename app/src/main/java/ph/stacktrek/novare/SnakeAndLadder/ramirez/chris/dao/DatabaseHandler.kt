package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASENAME,null, DATABASEVERSION) {

    companion object {
        private val DATABASEVERSION = 1
        private val DATABASENAME = "PlayerWinnerDatabase"
        const val TABLE_PLAYER = "player_table"
        const val TABLE_PLAYER_ID = "id"
        const val TABLE_PLAYER_NAME = "name"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PLAYERS_TABLE =
            "CREATE TABLE $TABLE_PLAYER" +
                    "($TABLE_PLAYER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TABLE_PLAYER_NAME TEXT)"

        db?.execSQL(CREATE_PLAYERS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PLAYER")
        onCreate(db)
    }
}