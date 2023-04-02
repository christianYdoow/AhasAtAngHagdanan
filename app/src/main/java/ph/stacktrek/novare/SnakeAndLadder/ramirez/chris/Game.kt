package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityGameBinding

class Game : AppCompatActivity() {

    private lateinit var  binding: ActivityGameBinding
    private lateinit var boardLayout: GridLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardLayout = findViewById(R.id.board_layout)

        boardLayout.columnCount = 10
        boardLayout.rowCount = 10

        val board = Array(10) { Array(10) { false } }


        for (row in 9 downTo  0) {
            for (col in 0 until 10) {
                val tileFrame = FrameLayout(this)

                // Set the size and background color of the tile
                tileFrame.layoutParams = GridLayout.LayoutParams().apply {
                    width = 125 // 100 pixels
                    height = 125 // 100 pixels
                    setMargins(3, 3, 3, 3) // 2 pixels margin on all sides
                }
                tileFrame.setBackgroundColor(if ((row + col) % 2 == 0) Color.rgb(76, 175, 80) else Color.rgb(205, 220, 57))

                // Create the image view for the tile
                val tileImage = ImageView(this)
                tileImage.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                tileImage.setImageResource(R.drawable.tile)

                // Create the text view with the cell number
                val number = if (row % 2 != 0) {
                    (row + 1) * 10 - col
                } else {
                    row * 10 + col + 1
                }
                val text = TextView(this).apply {
                    text = number.toString()
                    textSize = 12f
                    gravity = Gravity.CENTER // set gravity to center
                    setTypeface(null, Typeface.BOLD)
                }

                // Add the image view and text view to the tile frame
                tileFrame.addView(tileImage)
                tileFrame.addView(text)


                // Add the tile frame to the grid layout
                boardLayout.addView(tileFrame)
            }
        }

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




}