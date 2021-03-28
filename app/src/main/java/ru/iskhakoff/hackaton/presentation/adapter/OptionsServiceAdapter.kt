package ru.iskhakoff.hackaton.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.model.response.Option
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.presentation.models.OptionServiceEntity
import java.sql.SQLOutput

class OptionsServiceAdapter(private val inflater: LayoutInflater,
                            private val listener : ISelectedItem) :
    ListAdapter<Option, OptionsServiceAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Option>() {
            override fun areItemsTheSame(oldItem: Option, newItem: Option): Boolean {
                return oldItem.active == newItem.active
            }

            override fun areContentsTheSame(oldItem: Option, newItem: Option): Boolean {
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

    inner class ViewHolder(inflater: LayoutInflater,
                     parent : ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_option_service, parent, false)) {
        private val parentContainer : ViewGroup = itemView.findViewById(R.id.parent_option_service_container)
        private val titleOption : TextView = itemView.findViewById(R.id.title_option_service)
        private val descriptionOption : TextView = itemView.findViewById(R.id.description_option_service)

        fun bind(option : Option) {
            if (option.active) {
                parentContainer.setBackgroundColor(ContextCompat.getColor(inflater.context, R.color.teal_200))
            } else {
                parentContainer.setBackgroundColor(ContextCompat.getColor(inflater.context, R.color.white))
            }
            titleOption.text = option.name

            itemView.setOnClickListener {
                listener.selectedItem(layoutPosition)
            }
        }
    }

    override fun submitList(list: List<Option>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    interface ISelectedItem {
        fun selectedItem(position : Int)
    }
}