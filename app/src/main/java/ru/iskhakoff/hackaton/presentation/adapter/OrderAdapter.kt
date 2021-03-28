package ru.iskhakoff.hackaton.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.R

class OrderAdapter(private val inflater: LayoutInflater) : ListAdapter<Pair<String, String>, OrderAdapter.ViewHolder>(DIFF_UTIL){

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pair<String, String>>(){
            override fun areItemsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem.first == newItem.first
            }

            override fun areContentsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(inflater: LayoutInflater, parent : ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.item_title, parent, false)
    ) {
        val titleItem : TextView = itemView.findViewById(R.id.title_item)
        val valueItem : TextView = itemView.findViewById(R.id.value_item)

        fun bind (item : Pair<String, String>) {
            titleItem.text = item.first
            valueItem.text = item.second
        }
    }
}