package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityMainBinding

class GameMenuActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}