package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )
        changeFragment(FirstFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.instruction -> changeFragment (FirstFragment())
                R.id.play -> changeFragment(SecondFragment())
                R.id.leaderboard -> changeFragment(ThirdFragment())
                else->{

                }
            }
            true
        }
    }


    private fun changeFragment(fragment: Fragment){
        val fragmentManager =supportFragmentManager
        val fragmentTransaction =fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }







}