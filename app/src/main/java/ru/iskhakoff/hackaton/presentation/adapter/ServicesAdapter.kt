package ru.iskhakoff.hackaton.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.presentation.models.ServiceEntity

class ServicesAdapter(private val inflater: LayoutInflater, private val listener : IClickService) : ListAdapter<Service, ServicesAdapter.ViewHolder>(DIFF_UTIL){

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Service>(){
            override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(inflater : LayoutInflater,
                     parent : ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_service, parent, false)) {

        private val title : TextView = itemView.findViewById(R.id.title_service)

        fun bind(service : Service) {
            title.text = service.name

            itemView.setOnClickListener {
                listener.itemClickService(service.id)
            }
        }
    }

    override fun submitList(list: List<Service>?) {
        super.submitList(list?.let { ArrayList(it) })
    }


    interface IClickService {
        fun itemClickService(id : Int)
    }
}