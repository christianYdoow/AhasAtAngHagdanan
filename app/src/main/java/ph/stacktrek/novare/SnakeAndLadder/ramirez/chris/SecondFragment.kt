package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerDAO
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.FragmentSecondBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSecondBinding
    private lateinit var playerDAO: PlayerDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater, container, false)
        setupPlayerNumberSelection()
        savePlayers()

        return binding.root
    }
    private fun savePlayers(){
        binding.multiPlayerButton.setOnClickListener {
            val playerNames = ArrayList<String>()
            val playerColors = ArrayList<String>()
            val playerPositions = ArrayList<Int>()

            if (binding.checkboxOne.isChecked) {
                playerNames.add(binding.player1Name.text.toString())
                playerColors.add("RED")
                playerPositions.add(1)
                playerNames.add(binding.player2Name.text.toString())
                playerColors.add("BLUE")
                playerPositions.add(1)
            } else if (binding.checkboxTwo.isChecked) {
                playerNames.add(binding.player1Name.text.toString())
                playerColors.add("RED")
                playerPositions.add(1)
                playerNames.add(binding.player2Name.text.toString())
                playerColors.add("BLUE")
                playerPositions.add(1)
                playerNames.add(binding.player3Name.text.toString())
                playerColors.add("GREEN")
                playerPositions.add(1)
            } else if (binding.checkboxThree.isChecked) {
                playerNames.add(binding.player1Name.text.toString())
                playerColors.add("RED")
                playerPositions.add(1)
                playerNames.add(binding.player2Name.text.toString())
                playerColors.add("BLUE")
                playerPositions.add(1)
                playerNames.add(binding.player3Name.text.toString())
                playerColors.add("GREEN")
                playerPositions.add(1)
                playerNames.add(binding.player4Name.text.toString())
                playerColors.add("YELLOW")
                playerPositions.add(1)
            }

            val sharedPreferences = activity?.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putInt("numPlayers", playerNames.size)
            for (i in 0 until playerNames.size) {
                editor?.putString("playerName$i", playerNames[i])
                editor?.putString("playerColor$i", playerColors[i])
                editor?.putInt("playerPosition$i", playerPositions[i])
            }
            editor?.apply()
            // Start the Game activity
            val intent = Intent(activity, Game::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("playerNames", playerNames)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun setupPlayerNumberSelection() {
        binding.checkboxOne.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxTwo.isChecked = false
                binding.checkboxThree.isChecked = false
                binding.player1Name.visibility = View.VISIBLE
                binding.player2Name.visibility = View.VISIBLE
                binding.player3Name.visibility = View.GONE
                binding.player4Name.visibility = View.GONE
            } else {
                binding.player1Name.visibility = View.GONE
                binding.player2Name.visibility = View.GONE
            }
        }

        binding.checkboxTwo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxOne.isChecked = false
                binding.checkboxThree.isChecked = false
                binding.player1Name.visibility = View.VISIBLE
                binding.player2Name.visibility = View.VISIBLE
                binding.player3Name.visibility = View.VISIBLE
                binding.player4Name.visibility = View.GONE
            } else {
                binding.player1Name.visibility = View.GONE
                binding.player2Name.visibility = View.GONE
                binding.player3Name.visibility = View.GONE
            }
        }

        binding.checkboxThree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxOne.isChecked = false
                binding.checkboxTwo.isChecked = false
                binding.player1Name.visibility = View.VISIBLE
                binding.player2Name.visibility = View.VISIBLE
                binding.player3Name.visibility = View.VISIBLE
                binding.player4Name.visibility = View.VISIBLE
            } else {
                binding.player1Name.visibility = View.GONE
                binding.player2Name.visibility = View.GONE
                binding.player3Name.visibility = View.GONE
                binding.player4Name.visibility = View.GONE
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}