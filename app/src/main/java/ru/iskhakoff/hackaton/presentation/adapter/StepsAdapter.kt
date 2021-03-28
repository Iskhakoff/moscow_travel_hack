package ru.iskhakoff.hackaton.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.R

class StepsAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    private val listTabs = listOf("Услуги", "Опции", "Рейс", "Уточнение", "Контакты", "Подтверждение")

    inner class ViewHolder(inflater: LayoutInflater, parent : ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_step, parent, false)){
        private val title : TextView = itemView.findViewById(R.id.title_step)

        fun bind (titleText: String) {
            title.text = titleText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTabs[position])
    }

    override fun getItemCount(): Int {
        return listTabs.size
    }

    interface ITabSelected {
        fun tabSelected(id : Int)
    }
}