package com.example.faceithelper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.faceithelper.api.faceitApi
import com.example.faceithelper.api.models.Game
import com.example.faceithelper.databinding.GameListItemBinding
import kotlinx.coroutines.launch

internal class GameAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onClickListener: OnClickListener
) : ListAdapter<Game, GameAdapter.GameViewHolder>(GameDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(game)
        }
        holder.bind(game)
    }

    class GameViewHolder(var binding: GameListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game) {
            binding.lifecycleOwner!!.lifecycleScope.launch {
                binding.game = faceitApi.getGameInfo(item.gameName)
                binding.executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (game: Game) -> Unit) {
        fun onClick(game: Game) = clickListener(game)
    }

    companion object GameDiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.gameName == newItem.gameName
        }
    }
}
