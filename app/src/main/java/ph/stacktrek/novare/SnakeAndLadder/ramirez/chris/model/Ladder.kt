package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model

import android.icu.text.Transliterator.Position

class Ladder(val start:Int,val end:Int) {
    //this init ensure that the end integer position given is greater than the start integer position
    init{
        require(end > start){
            "end position must be greater than start"
        }
    }

    fun isOnLadder(position: Int):Boolean{
        return position == start
    }

    fun getEndPosition():Int{
        return end
    }

    //this function help for debugging and displaying snake in the game board
    override fun toString(): String {
        return "Ladder at : $start -> $end"
    }

}