package com.example.project3hearthstone.homescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.HomescreenViewHolderBinding
import com.example.project3hearthstone.network.InfoData

class HomeScreenAdapter(val clickListener: ClassListener): ListAdapter<String, HomeScreenAdapter.HomeScreenViewHolder>(HomeScreenDiffCallback()){

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
        holder.wholeThing.setOnClickListener{
            clickListener.onClick(item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomescreenViewHolderBinding.inflate(layoutInflater, parent, false)
        return HomeScreenViewHolder(binding)
    }

    class HomeScreenViewHolder(val binding: HomescreenViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        //val className: TextView = binding.findViewById(R.id.className)
        val className: TextView = binding.className
        val classEmblem: ImageView = binding.classEmblem
        val wholeThing: View = binding.wholeThing

        fun bind(item: String, clickListener: ClassListener) {
            className.text = item
            binding.clickListener = clickListener
            pickLogo(item, this)
        }
        private fun pickLogo(name: String, holder: HomeScreenViewHolder){
            when(name){
                "Mage" -> holder.classEmblem.setImageResource(R.drawable.mage_logo)
                "Druid" -> holder.classEmblem.setImageResource(R.drawable.druid_logo)
                "Hunter" -> holder.classEmblem.setImageResource(R.drawable.hunter_logo)
                "Priest" -> holder.classEmblem.setImageResource(R.drawable.priest_logo)
                "Rogue" -> holder.classEmblem.setImageResource(R.drawable.rogue_logo)
                "Paladin" -> holder.classEmblem.setImageResource(R.drawable.paladin_logo)
                "Shaman" -> holder.classEmblem.setImageResource(R.drawable.shaman_logo)
                "Warlock" -> holder.classEmblem.setImageResource(R.drawable.warlock_logo)
                "Warrior" -> holder.classEmblem.setImageResource(R.drawable.warrior_logo)
                "Demon Hunter" -> holder.classEmblem.setImageResource(R.drawable.demonhunter_logo)
                else -> holder.classEmblem.setImageResource(R.drawable.logo)
            }
        }
    }
}
class HomeScreenDiffCallback: DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
class ClassListener(val clickListener: (classA: String)-> Unit){
    fun onClick(classesA: String) = clickListener(classesA)
}
