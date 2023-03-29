package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityGameMenuBinding
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityMainBinding

class GameMenuActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityGameMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.singlePlayerButton.setOnClickListener{
            val goToMainActivity= Intent(
                applicationContext,
                MainActivity::class.java
            )

            startActivity(goToMainActivity)
            finish()
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false }, 2000)
    }




}