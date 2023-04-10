package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model

import android.widget.GridLayout
//val size:Int, val snake:List<Snake>, val ladder:List<Ladder>
class Board{
    private val size=100
    private val winTile=100
    private var currentPlayer=1
    private var gameOver = false

    private val board = Array(size) { IntArray(size) }

    private val snakes = listOf(
        Snake(8, 4),
        Snake(18, 1),
        Snake(26, 10),
        Snake(39, 5),
        Snake(51, 6),
        Snake(54, 36),
        Snake(56, 1),
        Snake(60, 23),
        Snake(75, 28),
        Snake(85, 59),
        Snake(90, 48),
        Snake(92, 25),
        Snake(97, 87),
        Snake(99, 63),
    )
    private val ladders = listOf(
        Ladder(3, 20),
        Ladder(6, 14),
        Ladder(11, 28),
        Ladder(15, 34),
        Ladder(17, 74),
        Ladder(22, 37),
        Ladder(38, 59),
        Ladder(49, 67),
        Ladder(57,76),
        Ladder(61,78),
        Ladder(73,86),
        Ladder(81,98),
        Ladder(88,91)
    )

    init {
        resetBoard()
    }

    fun resetBoard() {
        currentPlayer = 1
        gameOver = false
        for (i in 0 until size) {
            for (j in 0 until size) {
                board[i][j] = i * size + j + 1
            }
        }
    }

    fun printBoard() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                print("${board[i][j]} ")
            }
            println()
        }
    }

    fun checkSnakesAndLadders(row: Int, col: Int) {
        val cellValue = board[row][col]
        val snake = snakes.find { it.head == cellValue }
        if (snake != null) {
            println("Oops, you've landed on a snake! Move to cell ${snake.tail}")
            board[row][col] = snake.tail
        }

        val ladder = ladders.find { it.bottom == cellValue }
        if (ladder != null) {
            println("Yay, you've landed on a ladder! Climb up to cell ${ladder.top}")
            board[row][col] = ladder.top
        }
    }

//    fun nextTurn() {
//        val currentPlayer = playersList[currentPlayerIndex]
//        val diceRoll = rollDice()
//        movePlayer(currentPlayer, diceRoll)
//
//        // Do something for the current player's turn here
//        // ...
//
//        // Increment the index for the next player's turn
//        currentPlayerIndex++
//        if (currentPlayerIndex >= playersList.size) {
//            currentPlayerIndex = 0 // Reset to the first player in the list
//        }
//    }








//    fun movePlayer(player: Player, diceRoll: Int): Int {
//            val newPosition = player.position + diceRoll
//            if (newPosition > size) {
//                // Player has overshot the end of the board, move them back to the end
//                val diff = newPosition - size
//                player.position = size - diff
//                return player.position
//            } else {
//                player.position = newPosition
//                // Check for snakes or ladders at the new position
//                for (snake in snakes) {
//                    if (snake.head == newPosition) {
//                        player.position = snake.tail
//                        return player.position
//                    }
//                }
//                for (ladder in ladders) {
//                    if (ladder.bottom == newPosition) {
//                        player.position = ladder.top
//                        return player.position
//                    }
//                }
//                // No snakes or ladders, just return the new position
//                return player.position
//            }
//        }

    data class Snake(val head: Int, val tail: Int)
    data class Ladder(val bottom: Int, val top: Int)

}