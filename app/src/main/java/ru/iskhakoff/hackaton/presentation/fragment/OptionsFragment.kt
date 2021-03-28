package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.App
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.impl.ApiProviderImpl
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.data.storage.impl.PrefsProviderImpl
import ru.iskhakoff.hackaton.presentation.adapter.OptionsServiceAdapter
import ru.iskhakoff.hackaton.presentation.helper.OptionsServiceViewModelFactory
import ru.iskhakoff.hackaton.presentation.viewmodel.OptionsServiceViewModel

class OptionsFragment : Fragment(), OptionsServiceAdapter.ISelectedItem{

    private lateinit var mViewModel : OptionsServiceViewModel

    private lateinit var mServiceTitle : TextView
    private lateinit var mServiceDescription : TextView
    private lateinit var mRecycler : RecyclerView
    private lateinit var mConfirm : Button

    private lateinit var adapter : OptionsServiceAdapter
    private lateinit var app : App

    companion object {
        private const val EXTRA_SERVICE_KEY = "service"
        fun setBundle(service : Service) : Bundle {
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_SERVICE_KEY, service)
            return bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val viewmodelFactory = OptionsServiceViewModelFactory(apiProvider, prefsProvider)
        mViewModel = ViewModelProvider(this, viewmodelFactory).get(OptionsServiceViewModel::class.java)
        app = App.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_options, container, false)
        setupUI(rootView)
        setupRecycler()
        val service = arguments?.getSerializable(EXTRA_SERVICE_KEY) as Service?

        service?.let {
            mViewModel.setOptionsService(it.children)
            mServiceTitle.text = it.name
            mServiceDescription.text = it.description
        }

        mViewModel.getOptionsServiceObservable().observe(viewLifecycleOwner, {
            it.forEach { option ->
                if (option.active) {
                    app.setSubServiceId(option.id)
                }
            }
            adapter.submitList(it)
        })

        mConfirm.setOnClickListener {
            mViewModel.sendServicesIds(app.getMainServiceId(), app.getSubServicesIds())
            findNavController().navigate(R.id.flight)
        }

        return rootView
    }

    override fun selectedItem(position: Int) {
        mViewModel.setOptionActive(position)
    }

    private fun setupRecycler() {
        adapter = OptionsServiceAdapter(layoutInflater, this)
        val lm = LinearLayoutManager(requireContext())
        mRecycler.layoutManager = lm
        mRecycler.isNestedScrollingEnabled = false
        mRecycler.itemAnimator = null
        mRecycler.adapter = adapter
    }

    private fun setupUI(rootView: View) {
        mServiceTitle = rootView.findViewById(R.id.options_title_service)
        mServiceDescription = rootView.findViewById(R.id.options_description_service)
        mRecycler = rootView.findViewById(R.id.options_recycler)
        mConfirm = rootView.findViewById(R.id.confirm_options)
    }
}