package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.impl.ApiProviderImpl
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.data.storage.impl.PrefsProviderImpl
import ru.iskhakoff.hackaton.presentation.adapter.OrderAdapter
import ru.iskhakoff.hackaton.presentation.adapter.decoration.DefaultItemDecoration
import ru.iskhakoff.hackaton.presentation.helper.ConfirmViewModelFactory
import ru.iskhakoff.hackaton.presentation.viewmodel.ConfirmViewModel

class ConfirmFragment : Fragment() {

    private lateinit var mRecycler : RecyclerView
    private lateinit var mConfirmBtn : Button

    private lateinit var mViewModel : ConfirmViewModel

    private lateinit var adapter : OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val viewModelFactory = ConfirmViewModelFactory(apiProvider, prefsProvider)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(ConfirmViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_confirm, container, false)
        setupUI(rootView)
        setupRecycler()

        mViewModel.getOrder()

        mViewModel.getTotalObservable().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        mViewModel.getStatusConfirmedObservable().observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.success_info)
        })

        mConfirmBtn.setOnClickListener {
            mViewModel.confirmOrder()
        }

        return rootView
    }

    private fun setupRecycler() {
        adapter = OrderAdapter(layoutInflater)
        mRecycler.layoutManager = LinearLayoutManager(requireContext())
        mRecycler.addItemDecoration(DefaultItemDecoration(resources.getDimension(R.dimen.default_margin).toInt()))
        mRecycler.adapter = adapter
    }

    private fun setupUI(rootView: View) {
        mRecycler = rootView.findViewById(R.id.order_recycler)
        mConfirmBtn = rootView.findViewById(R.id.confirm_order)
    }
}