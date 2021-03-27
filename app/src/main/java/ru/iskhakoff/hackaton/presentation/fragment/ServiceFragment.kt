package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import ru.iskhakoff.hackaton.R

class ServiceFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_service, container, false)

//        val items = listOf("Option 1", "Option 2", "Option 3")
//        val labelOptions = rootView.findViewById<AutoCompleteTextView>(R.id.label_options)
//
//        labelOptions.setOnItemClickListener { adapterView, view, i, l ->
//            println("INDEX: $")
//        }
//
//        val adapter = ArrayAdapter(requireContext(), R.layout.item_services_list, items)
//        labelOptions.setAdapter(adapter)

        return rootView
    }
}