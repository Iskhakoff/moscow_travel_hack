package ru.iskhakoff.hackaton.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import ru.iskhakoff.hackaton.App
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.impl.ApiProviderImpl
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.data.storage.impl.PrefsProviderImpl
import ru.iskhakoff.hackaton.presentation.helper.FlightViewModelFactory
import ru.iskhakoff.hackaton.presentation.viewmodel.FlightViewModel
import java.text.SimpleDateFormat
import java.util.*

class FlightFragment : Fragment() {

    private lateinit var mRadioGroup : RadioGroup
    private lateinit var mSelectDate : ViewGroup
    private lateinit var mFieldNumberFlight : EditText
    private lateinit var mCheckBoxLoseFlightNumber : CheckBox
    private lateinit var mDateTextLabel : TextView
    private lateinit var mConfirmBtn : Button
    private lateinit var mDirectionIn : RadioButton
    private lateinit var mDirectionOut : RadioButton

    private lateinit var mViewModel : FlightViewModel
    private lateinit var app : App

    private lateinit var dateToRemote : String
    private lateinit var direction : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = App.getInstance()
        val apiProvider : ApiProvider = ApiProviderImpl()
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val viewModelFactory = FlightViewModelFactory(apiProvider, prefsProvider)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(FlightViewModel::class.java)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_flight, container, false)
        setupUI(rootView)

        val datePicker = MaterialDatePicker.Builder.datePicker().build()

        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)
            val format = SimpleDateFormat("dd.MM.yyyy").format(date)
            dateToRemote = SimpleDateFormat("yyyy-MM-dd").format(date)
            mDateTextLabel.text = format
        }

        mSelectDate.setOnClickListener {
            datePicker.showNow(requireActivity().supportFragmentManager, "datePicker")
        }

        mDirectionIn.setOnClickListener {
            direction = "in"
        }

        mDirectionOut.setOnClickListener {
            direction = "out"
        }


        mConfirmBtn.setOnClickListener {
            if (!mCheckBoxLoseFlightNumber.isChecked) {
                mViewModel.sendFlight(direction, dateToRemote, mFieldNumberFlight.text.toString())
            }
        }

        mViewModel.getStateObservable().observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.additional_info)
            } else {
                findNavController().navigate(R.id.not_provided)
            }
        })

        return rootView
    }

    private fun setupUI(rootView: View) {
        mRadioGroup = rootView.findViewById(R.id.direction_radio_group)
        mSelectDate = rootView.findViewById(R.id.select_date_action)
        mDirectionIn = rootView.findViewById(R.id.departure_radio)
        mDirectionOut = rootView.findViewById(R.id.arrival_radio)
        mDateTextLabel = rootView.findViewById(R.id.selected_date_label)
        mFieldNumberFlight = rootView.findViewById(R.id.number_flight)
        mCheckBoxLoseFlightNumber = rootView.findViewById(R.id.i_donot_know_flight_number)
        mConfirmBtn = rootView.findViewById(R.id.confirm_flight_btn)

    }
}