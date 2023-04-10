package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao


import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.Player

interface PlayerDAO {
    fun addPlayer(player:Player)

    fun getPlayer():List<Player>

    fun updatePlayer(player: Player)

    fun deletePlayer(player: Player)

}

class PlayerDAOStubImplementation:PlayerDAO{
    private var playerList:ArrayList<Player> = ArrayList()


    override fun addPlayer(player: Player) {
        playerList.add(player)
    }

    override fun getPlayer(): List<Player> {
        TODO("Not yet implemented")
    }

    override fun updatePlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun deletePlayer(player: Player) {
        TODO("Not yet implemented")
    }

}

