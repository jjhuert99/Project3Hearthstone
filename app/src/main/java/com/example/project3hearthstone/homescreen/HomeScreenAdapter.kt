package com.example.project3hearthstone.homescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.R
import com.example.project3hearthstone.network.InfoData

class HomeScreenAdapter: RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder>(){
    var dataClasses = listOf<String>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount() = dataClasses.size

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        val item = dataClasses[position]
        val res = holder.itemView.context.resources
        holder.className.text = item
        //holder.textView.text = item
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.homescreen_view_holder, parent, false)
        return HomeScreenViewHolder(view)
    }


    class HomeScreenViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val className: TextView = itemView.findViewById(R.id.className)
    }

}