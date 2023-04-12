package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.adapter.PlayerListAdapter
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerDAOSQLLiteImplementation
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerListDao
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityGameBinding
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.Player
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.PlayerColor
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.viewmodel.BoardViewModel

class Game : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var boardSurfaceView: SurfaceView
    private var boardCanvas: Canvas? = null
    private lateinit var binding: ActivityGameBinding
    private lateinit var boardLayout: BoardViewModel
    private lateinit var player: Player



    private  var doubleBackToExitPressedOnce = false
    private var players = mutableListOf<Player>()
    private lateinit var sharedPreferences: SharedPreferences

    private var winTile=100
    private var round = 1
    var currentPlayerIndex = 0
    var rollResult = 0
    private lateinit var adapter: PlayerListAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

        boardLayout = binding.boardView

        sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)

        // Get the number of players
        val numPlayers = sharedPreferences.getInt("numPlayers", 0)

        // Get the player names, colors, and positions
        val playerNames = ArrayList<String>()
        val playerColors = ArrayList<String>()
        val playerPositions = ArrayList<Int>()



        for (i in 0 until numPlayers) {
            playerNames.add(sharedPreferences.getString("playerName$i", "")!!)
            playerColors.add(sharedPreferences.getString("playerColor$i", "")!!)
            playerPositions.add(sharedPreferences.getInt("playerPosition$i", 0))
            println(" ${playerNames[i]} ,${playerColors[i]},${playerPositions[i]}")
            player = Player(playerNames[i], getColorFromString(playerColors[i]), playerPositions[i])
            players.add(player)
            boardLayout.updatePlayerList(players)

        }
        setPlayerLayouts(playerNames)
        // Use the player data to start the game

        startGame(players)



    }

    fun getColorFromString(colorString: String): PlayerColor {
        return when (colorString) {
            "RED" -> PlayerColor.RED
            "GREEN" -> PlayerColor.GREEN
            "BLUE" -> PlayerColor.BLUE
            "YELLOW" -> PlayerColor.YELLOW
            else -> throw IllegalArgumentException("Invalid color: $colorString")
        }
    }


    @SuppressLint("SetTextI18n")
    fun startGame(players:MutableList<Player>){
        val playerWinner = ArrayList<PlayerListDao>()
        adapter = PlayerListAdapter(playerWinner)
        val roundTextView =binding.round
        val rollButton=binding.dice
        roundTextView.text = "Round $round"
        val diceImage=binding.diceImage
        rollButton.setOnClickListener{
            val currentPlayer = players[currentPlayerIndex]
            if(currentPlayer.currentPosition == winTile){
                boardLayout.gameOver()
                boardLayout.winner(currentPlayer)
                saveWinner(currentPlayer.name)
                reset(currentPlayer)
            }else{
                rollResult = boardLayout.roll()
                val drawableResource =when (rollResult){
                    1 -> R.drawable.dice_one
                    2 -> R.drawable.dice_two
                    3 -> R.drawable.dice_three
                    4 -> R.drawable.dice_four
                    5 -> R.drawable.dice_five
                    else -> R.drawable.dice_six
                }
                diceImage.setImageResource(drawableResource)

                currentPlayer.currentPosition +=rollResult
                currentPlayer.currentPosition = boardLayout.movePlayerIfOnLadder(currentPlayer.currentPosition, boardLayout.ladders,applicationContext,currentPlayer)
                currentPlayer.currentPosition = boardLayout.movePlayerIfOnSnake(currentPlayer.currentPosition, boardLayout.snakes,applicationContext,currentPlayer)

                boardLayout.invalidate()


            }
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size
            if(currentPlayerIndex == 0){
                round++
                println("current round is $round")

                Handler().postDelayed({
                    roundTextView.text="Round $round"

                },2000)

            }
            if (currentPlayer.currentPosition >= winTile){
                println("${currentPlayer.name} is the winner congratulations")
                saveWinner(currentPlayer.name)
                reset(currentPlayer)

            }else{
                println("${currentPlayer.name} your position ${currentPlayer.currentPosition}")
            }



        }

    }

    fun reset(player: Player){
        showNotificationWinner(player)
        round=1
        currentPlayerIndex=0
        for (player in players){
            player.currentPosition=1
        }
        boardLayout.invalidate()
        hideSystemUI()

    }


    fun saveWinner(playerWinnerName:String){
        val playerListDao = PlayerListDao("")
        playerListDao.name=playerWinnerName
        val playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)
        playerDAO.addPlayer(playerListDao)
        adapter.addPlayer(playerListDao)
    }

    @SuppressLint("SetTextI18n")
    private fun setPlayerLayouts(playerNames: List<String>) {
        val playerLayouts = listOf(
            binding.player1Layout,
            binding.player2Layout,
            binding.player3Layout,
            binding.player4Layout
        )

        for ((index, playerLayout) in playerLayouts.withIndex()) {
            val playerName = playerNames.getOrNull(index)

            if (playerName != null) {
                playerLayout.text = "$playerName"
                playerLayout.setBackgroundColor(Color.LTGRAY)
                playerLayout.setTextColor(ContextCompat.getColor(this, R.color.black))
            } else {
                playerLayout.text = "No player"
                playerLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
                playerLayout.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }
    }

    fun showNotificationWinner(player: Player) {
        return this.let{
            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Title of dialog")
            builder.setMessage("${player.name} congratulation you win")
            builder.setPositiveButton("OK") { dialog, which ->

            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    fun showNotificationIsOnSnake(){
        return this.let{
            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Title of dialog")
            builder.setMessage("Oh no!... ${player.name} you step on snake")
            builder.setPositiveButton("OK") { dialog, which ->
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
    fun showNotificationIsOnLadder(){
        return this.let{
            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Title of dialog")
            builder.setMessage("Yehheey yes!... ${player.name} you step on ladder")
            builder.setPositiveButton("OK") { dialog, which ->
            }
            val dialog = builder.create()
            dialog.show()
        }
    }



    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

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

    override fun surfaceCreated(holder: SurfaceHolder) {
        boardCanvas=holder.lockCanvas()
        boardLayout.drawSnakeAndLadderBoard(boardCanvas, players)
        holder.unlockCanvasAndPost(boardCanvas)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }
}



