package com.example.project3hearthstone.classscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.databinding.ClassScreenViewHolderBinding
import com.example.project3hearthstone.network.CardsByClass

class CardsByClassAdapter(val clickListener: OnClickListener): ListAdapter<CardsByClass, CardsByClassAdapter.CardsByClassViewHolder>(DiffCallback){
    class CardsByClassViewHolder(private var binding: ClassScreenViewHolderBinding): RecyclerView.ViewHolder(binding.root){
       val cardsByClassHolder: View = binding.cardByClassHolder
        fun bind(cardsShow: CardsByClass){
            binding.classCard = cardsShow
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<CardsByClass>(){
        override fun areItemsTheSame(oldItem: CardsByClass, newItem: CardsByClass): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CardsByClass, newItem: CardsByClass): Boolean {
            return oldItem.cardId == newItem.cardId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsByClassViewHolder {
        return CardsByClassViewHolder(ClassScreenViewHolderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CardsByClassViewHolder, position: Int) {
        val cardInfo = getItem(position)
        holder.bind(cardInfo)
        holder.cardsByClassHolder.setOnClickListener{
            clickListener.onClick(cardInfo.name)
        }
    }

    class OnClickListener(val clickListener: (cardA: String)-> Unit){
        fun onClick(cardA: String) = clickListener(cardA)
    }
}
