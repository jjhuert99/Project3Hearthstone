package com.example.project3hearthstone.favoritesscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.databinding.FavoritesViewHolderBinding
import com.example.project3hearthstone.favoritesdatabase.Favorite

class FavoritesAdapter(val clickListener: OnClickListener): ListAdapter<Favorite, FavoritesAdapter.FavViewHolder>(DiffCallback){

    class FavViewHolder(private var binding: FavoritesViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        val favInfoHolder: View = binding.favoritesHolder
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(FavoritesViewHolderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val favCard = getItem(position)
        holder.bind(favCard)
        holder.favInfoHolder.setOnClickListener{
            clickListener.onClick(favCard.cardName)
        }
    }

    class OnClickListener(val clickListener: (favName: String)-> Unit){
        fun onClick(favName: String) = clickListener(favName)
    }
}
