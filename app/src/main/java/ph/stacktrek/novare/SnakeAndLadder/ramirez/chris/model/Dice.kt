package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model

import kotlin.random.Random

class Dice {
    private val numSides = 6

    fun roll():Int{
        return Random.nextInt(1,numSides +1)
    }
}