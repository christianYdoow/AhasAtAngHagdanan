package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model

import android.graphics.Color

data class Player(
    var name: String,
    val color: PlayerColor,
    var currentPosition: Int
){
    lateinit var id: String
}