package ru.iskhakoff.hackaton.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.model.response.Form

class FormsAdapter(private val inflater: LayoutInflater, private val listener : ITextChanged) : ListAdapter<Form, FormsAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Form>(){
            override fun areItemsTheSame(oldItem: Form, newItem: Form): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: Form, newItem: Form): Boolean {
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

    inner class ViewHolder(inflater : LayoutInflater, parent : ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_form, parent, false)) {
        private val formTitle : TextView = itemView.findViewById(R.id.form_title)
        private val valueField : EditText = itemView.findViewById(R.id.form_value)

        fun bind(form : Form) {
            formTitle.text = form.name

            valueField.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listener.textChanged(form.key, p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }
    }

    interface ITextChanged {
        fun textChanged(key : String, value : String)
    }
}