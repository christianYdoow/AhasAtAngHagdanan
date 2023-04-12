package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.adapter.PlayerListAdapter
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerDAO
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerDAOSQLLiteImplementation
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerListDao

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var playerListAdapter: PlayerListAdapter
    private lateinit var playerDao:PlayerDAO
    private lateinit var playerList: ArrayList<PlayerListDao>
    lateinit var adapter: PlayerListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        playerList = ArrayList<PlayerListDao>()
        playerListAdapter = PlayerListAdapter(playerList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_third, container, false)
        // Inflate the layout for this fragment
        val max_playerWinner= 5
        playerDao = PlayerDAOSQLLiteImplementation(requireContext())
        val playerList = playerDao.getPlayer()

        playerListAdapter = PlayerListAdapter(playerList as ArrayList<PlayerListDao>)

        rootView.findViewById<RecyclerView>(R.id.player_list_recyclerview).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playerListAdapter
            if(playerList.size>max_playerWinner){
                playerList.removeAt(0)
                playerListAdapter.notifyItemRemoved(0)
            }
        }
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }

            }
    }




}