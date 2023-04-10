package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model

class Snake(val start:Int, val end:Int) {
    //this init ensure that the start integer position given is greater than the end integer position
    init{
        require(start>end){
            "Start position must be greater than end"
        }
    }

    fun isOnHeadSnake(position: Int):Boolean{
        return position == start
    }

    fun getTailPosition():Int{
        return end
    }

    //this function help for debugging and displaying snake in the game board
    override fun toString(): String {
        return "Snake at : $start -> $end"
    }
}