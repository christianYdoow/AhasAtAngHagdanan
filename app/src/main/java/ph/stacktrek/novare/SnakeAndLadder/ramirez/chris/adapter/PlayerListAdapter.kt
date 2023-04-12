package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.dao.PlayerListDao
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.databinding.ListItemPlayerBinding


class PlayerListAdapter(
                        private val playerListDaos: MutableList<PlayerListDao>
):
                        RecyclerView.Adapter<PlayerListAdapter.ViewHolder>()

{

    fun addPlayer(playerListDao : PlayerListDao) {
           playerListDaos.add(0,playerListDao)
           notifyItemInserted(0)
       }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerListAdapter.ViewHolder {
        val playerBinding = ListItemPlayerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(playerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(playerListDaos[position])
    }


    override fun getItemCount(): Int=playerListDaos.size

    inner class ViewHolder(private val playerBinding: ListItemPlayerBinding ):
            RecyclerView.ViewHolder(playerBinding.root){

                fun bindItems(playerListDao: PlayerListDao){
                    playerBinding.playerNameTextView.text=playerListDao.name
                }
            }



}