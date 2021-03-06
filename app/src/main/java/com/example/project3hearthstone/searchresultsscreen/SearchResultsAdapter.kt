package com.example.project3hearthstone.searchresultsscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.databinding.SearchViewHolderBinding
import com.example.project3hearthstone.network.CardsBySearch

class SearchResultsAdapter(val clickListener: OnClickListener): ListAdapter<CardsBySearch, SearchResultsAdapter.ResultsViewHolder>(DiffCallback){

    class ResultsViewHolder(private var binding: SearchViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        val searchResultsHolder: View = binding.searchResultsHolder
        fun bind(searchResults: CardsBySearch){
            binding.searchResultsVH = searchResults
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<CardsBySearch>() {
        override fun areItemsTheSame(oldItem: CardsBySearch, newItem: CardsBySearch): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CardsBySearch, newItem: CardsBySearch): Boolean {
            return oldItem.cardId == newItem.cardId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsAdapter.ResultsViewHolder {
        return ResultsViewHolder(SearchViewHolderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchResultsAdapter.ResultsViewHolder, position: Int) {
        val cardResult = getItem(position)
        holder.bind(cardResult)
        holder.searchResultsHolder.setOnClickListener{
            clickListener.onClick(cardResult.name!!)
        }
    }

    class OnClickListener(val clickListener: (queryA: String)-> Unit){
        fun onClick(queryA: String) = clickListener(queryA)
    }
}
