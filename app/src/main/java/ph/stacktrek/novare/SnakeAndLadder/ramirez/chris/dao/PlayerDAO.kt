package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException


interface PlayerDAO {
    fun addPlayer(playerListDao:PlayerListDao)

    fun getPlayer():List<PlayerListDao>

    fun updatePlayer(playerListDao: PlayerListDao)

    fun deletePlayer(playerListDao: PlayerListDao)

    fun deleteAllPlayer()


}

class PlayerDAOSQLLiteImplementation(var applicationContext : Context):PlayerDAO{

    override fun addPlayer(playerListDao: PlayerListDao) {
        val databaseHandler=DatabaseHandler(applicationContext)
        val db=databaseHandler.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(DatabaseHandler.TABLE_PLAYER_NAME,playerListDao.name)

        var status=db.insert(DatabaseHandler.TABLE_PLAYER,
            null,
            contentValues)
        db.close()
        println("na add na ang player")
    }

    override fun getPlayer(): ArrayList<PlayerListDao> {
        val databaseHandler=DatabaseHandler(applicationContext)
        val db=databaseHandler.readableDatabase
        var result=ArrayList<PlayerListDao>()

        var cursor: Cursor?=null
        val columns =  arrayOf(DatabaseHandler.TABLE_PLAYER_ID,
            DatabaseHandler.TABLE_PLAYER_NAME)

        try {
            cursor = db.query(DatabaseHandler.TABLE_PLAYER,
                columns,
                null,
                null,
                null,
                null,
                null)

        }catch (sqlException: SQLException){
            db.close()
            return result
        }


        if(cursor.moveToFirst()){
            do{
                var playerListDao = PlayerListDao("")
                playerListDao.name = cursor.getString(1)
                playerListDao.id = cursor.getInt(0).toString()
                result.add(playerListDao)
            }while(cursor.moveToNext())
        }

        println("na kuha na ang player")
        return result
    }

    override fun updatePlayer(playerListDao: PlayerListDao) {
        TODO("Not yet implemented")
    }

    override fun deletePlayer(playerListDao: PlayerListDao) {
        TODO("Not yet implemented")
    }
    override fun deleteAllPlayer(){
        val databaseHandler = DatabaseHandler(applicationContext)
        val db = databaseHandler.writableDatabase
        db.delete(DatabaseHandler.TABLE_PLAYER, null, null)
        db.close()
    }

}

