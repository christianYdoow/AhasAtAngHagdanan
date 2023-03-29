package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.*
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        starProgress()

    }

    private fun starProgress(){
        val totalProgress = 100
        var currentProgress = 0

        Thread {
            while (currentProgress < totalProgress) {
                currentProgress++
                updateProgress(currentProgress)
                Thread.sleep(100) // delay to simulate progress updates
            }
            // When progress reaches 100%, move on to game-menu
            startActivity(Intent(this, GameMenuActivity::class.java))
            finish()
        }.start()
    }
    private fun updateProgress(progress: Int) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val progressBarText = findViewById<TextView>(R.id.progress_bar_text)
        runOnUiThread {
            progressBar.progress = progress
            progressBarText.text = "$progress %"
        }
    }
}