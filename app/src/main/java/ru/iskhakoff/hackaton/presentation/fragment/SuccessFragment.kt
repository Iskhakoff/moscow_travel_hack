package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.iskhakoff.hackaton.R

class SuccessFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_success, container, false)

        val returnBtn : Button = rootView.findViewById(R.id.return_start_dest_btn)

        returnBtn.setOnClickListener {
            findNavController().navigate(R.id.services)
        }

        return rootView
    }
}