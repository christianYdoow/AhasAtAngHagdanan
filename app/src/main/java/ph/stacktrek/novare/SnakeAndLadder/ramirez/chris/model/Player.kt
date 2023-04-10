package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model

import android.graphics.Color

data class Player(
    val name: String,
    val color: PlayerColor,
    var currentPosition: Int
)