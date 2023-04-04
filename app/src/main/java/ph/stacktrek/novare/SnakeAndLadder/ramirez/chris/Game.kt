package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityGameBinding

class Game : AppCompatActivity() {

    private lateinit var  binding: ActivityGameBinding
    private lateinit var boardLayout: GridLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardLayout = findViewById(R.id.board_layout)

        val sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        val playerNamesSet = sharedPreferences.getStringSet("playerNames", emptySet())
        val players = playerNamesSet?.toList()?: emptyList()

        setPlayerLayouts(players)


        val gameLogic = GameLogic(boardLayout,players )

        gameLogic.start()

        if (players != null) {
            for (player in players) {

                // Do something with the player name
                println(player)
            }
        }



        rollDice(binding.dice, binding.diceImage)

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit the game", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun setPlayerLayouts(players: List<String>) {
        binding.player1Layout.text = players.getOrNull(0) ?: "No player"
        if (binding.player1Layout.text == "No player") {
            binding.player1Layout.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))

        }

        binding.player2Layout.text = players.getOrNull(1) ?: "No player"
        if (binding.player2Layout.text == "No player") {
            binding.player2Layout.setTextColor(ContextCompat.getColor(this, R.color.orange))
        }

        binding.player3Layout.text = players.getOrNull(2) ?: "No player"
        if (binding.player3Layout.text == "No player") {
            binding.player3Layout.setTextColor(ContextCompat.getColor(this, R.color.orange))
        }

        binding.player4Layout.text = players.getOrNull(3) ?: "No player"
        if (binding.player4Layout.text == "No player") {
            binding.player4Layout.setTextColor(ContextCompat.getColor(this, R.color.orange))
        }
    }

}

private fun rollDice(button : Button,diceImage:ImageView){
    button.setOnClickListener{



        val randomNumber=(1..6).random()
        val drawableResource= when (randomNumber){
            1->R.drawable.dice_one
            2->R.drawable.dice_two
            3->R.drawable.dice_three
            4->R.drawable.dice_four
            5->R.drawable.dice_five
            else->R.drawable.dice_six
        }
        diceImage.setImageResource(drawableResource)

    }

}

class BoardGenerator {
    companion object {
        fun generateBoard(boardLayout: GridLayout): Map<Int, FrameLayout> {
            val board = Array(10) { Array(10) { false } }
            val tileMap = mutableMapOf<Int, FrameLayout>()

            val ladderPositions = mapOf(3 to 20, 6 to 14, 11 to 28, 15 to 34, 17 to 74, 22 to 37, 38 to 59, 49 to 67, 57 to 76,61 to 78,73 to 86,81 to 98,88 to 91)
            val snakePositions = mapOf(8 to 4, 18 to 1, 26 to 10, 39 to 5, 51 to 6, 54 to 36, 56 to 1, 60 to 23, 75 to 28, 85 to 59,90 to 48,92 to 25,97 to 87,99 to 63)

            for (row in 9 downTo 0) {
                for (col in 0 until 10) {
                    val tileFrame = FrameLayout(boardLayout.context)

                    // Set the size and background color of the tile
                    tileFrame.layoutParams = GridLayout.LayoutParams().apply {
                        width = 125 // 100 pixels
                        height = 125 // 100 pixels
                        setMargins(3, 3, 3, 3) // 2 pixels margin on all sides
                    }
                    tileFrame.setBackgroundColor(
                        if ((row + col) % 2 == 0) Color.rgb(
                            76,
                            175,
                            80
                        ) else Color.rgb(205, 220, 57)
                    )

                    // Create the image view for the tile
                    val tileImage = ImageView(boardLayout.context)
                    tileImage.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    tileImage.setImageResource(R.drawable.tile)

                    // Create the text view with the cell number
                    val number = if (row % 2 != 0) {
                        (row + 1) * 10 - col
                    } else {
                        row * 10 + col + 1
                    }
                    val text = TextView(boardLayout.context).apply {
                        text = number.toString()
                        textSize = 12f
                        gravity = Gravity.CENTER // set gravity to center
                        setTypeface(null, Typeface.BOLD)

                        if (ladderPositions.containsKey(number)) {
                            setTextColor(Color.rgb(40, 99, 237))
                        } else if (snakePositions.containsKey(number)) {
                            setTextColor(Color.rgb(148, 0, 211)) // Violet
                        }
                    }

                    // Add the image view and text view to the tile frame
                    tileFrame.addView(tileImage)
                    tileFrame.addView(text)

                    // Add the tile frame to the grid layout
                    boardLayout.addView(tileFrame)

                    // Add the tile frame to the tile map
                    tileMap[number] = tileFrame
                }
            }

            return tileMap
        }
    }
}

class Player(val name:String){
    var position:Int=0
    var isWinner:Boolean=false
}
class GameLogic(val boardLayout: GridLayout, private val players: List<String>?) {
    private var currentPlayerIndex = 0
    private var round = 1

    private val tokenSize = 60




    fun start() {
        // start the game here
        boardLayout.columnCount = 10
        boardLayout.rowCount = 10
        BoardGenerator.generateBoard(boardLayout)

//        players?.forEach { player ->
//            addPlayerToken(player)
//        }



    }

    private fun addPlayerToken(player: Player) {
        // Create a new View object for the player token
        val token = View(boardLayout.context)
        token.layoutParams = GridLayout.LayoutParams().apply {
            width = boardLayout.width / boardLayout.columnCount
            height = boardLayout.height / boardLayout.rowCount
            columnSpec = GridLayout.spec(player.position % boardLayout.columnCount)
            rowSpec = GridLayout.spec(player.position / boardLayout.columnCount)
        }
        token.setBackgroundColor(Color.RED)

        // Add the token to the board
        boardLayout.addView(token)
    }

//    private fun turn(player: Player) {
//        // player's turn logic here
//    }
//
//    private fun rollDice(): Int {
//        // roll dice logic here
//    }
//
//    private fun checkForWinner(): Player? {
//        // check for winner logic here
//    }
//
//    private fun gameOver(winner: Player) {
//        // game over logic here
//    }
}