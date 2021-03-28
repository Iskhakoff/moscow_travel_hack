package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.App
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.impl.ApiProviderImpl
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.data.storage.impl.PrefsProviderImpl
import ru.iskhakoff.hackaton.presentation.adapter.ServicesAdapter
import ru.iskhakoff.hackaton.presentation.helper.ServicesViewModelFactory
import ru.iskhakoff.hackaton.presentation.viewmodel.ServicesViewModel

class ServicesFragment : Fragment(), ServicesAdapter.IClickService {

    private lateinit var mViewModel : ServicesViewModel

    private lateinit var mRecycler : RecyclerView
    private lateinit var adapter : ServicesAdapter

    private lateinit var app : App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val viewModelFactory = ServicesViewModelFactory(apiProvider, prefsProvider)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(ServicesViewModel::class.java)
        app = App.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_service, container, false)
        setupUI(rootView)
        setupRecycler()

        mViewModel.getServices()

        mViewModel.getServicesObservable().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        mViewModel.getStateProvidedObservable().observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.not_provided)
        })

        mViewModel.getServiceObservable().observe(viewLifecycleOwner, {
            val bundle = OptionsFragment.setBundle(it)
            findNavController().navigate(R.id.options, bundle)
        })

        return rootView
    }

    private fun setupRecycler() {
        adapter = ServicesAdapter(layoutInflater, this)
        val lm = GridLayoutManager(requireContext(), 2)
        mRecycler.layoutManager = lm
        mRecycler.adapter = adapter
    }

    private fun setupUI(rootView: View) {
        mRecycler = rootView.findViewById(R.id.services_recycler)
    }

    override fun itemClickService(id: Int) {
        mViewModel.getService(id)
        app.setMainServiceId(id)
    }
}