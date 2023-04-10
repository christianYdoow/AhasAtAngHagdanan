package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.R
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.Player

class PlayerListAdapter(private val players:List<Player>):
    RecyclerView.Adapter<PlayerListAdapter.ViewHolder>(){

        class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            val playerNameTextView:TextView = itemView.findViewById(R.id.playerNameTextView)
            val playerPositionTextView : TextView = itemView.findViewById(R.id.playerPositionTextView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_game,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = players[position]
        holder.playerNameTextView.text = player.name
        holder.playerPositionTextView.text = "Position: ${player.currentPosition}"
    }
}