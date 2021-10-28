package com.example.project3hearthstone.favoritesscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.databinding.FavoritesViewHolderBinding
import com.example.project3hearthstone.favoritesdatabase.Favorite

class FavoritesAdapter: ListAdapter<Favorite, FavoritesAdapter.FavViewHolder>(DiffCallback){

    class FavViewHolder(private var binding: FavoritesViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favCard: Favorite){
            binding.favoriteCard = favCard
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.cardId == newItem.cardId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.FavViewHolder {
        return FavViewHolder(FavoritesViewHolderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavViewHolder, position: Int) {
        val favCard = getItem(position)
        holder.bind(favCard)
    }
}