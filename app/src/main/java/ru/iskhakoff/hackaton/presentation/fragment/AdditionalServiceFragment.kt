package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.hackaton.App
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.impl.ApiProviderImpl
import ru.iskhakoff.hackaton.data.remote.model.request.Form
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.data.storage.impl.PrefsProviderImpl
import ru.iskhakoff.hackaton.presentation.adapter.FormsAdapter
import ru.iskhakoff.hackaton.presentation.adapter.decoration.DefaultItemDecoration
import ru.iskhakoff.hackaton.presentation.helper.AdditionalViewModelFactory
import ru.iskhakoff.hackaton.presentation.viewmodel.AdditionalViewModel

class AdditionalServiceFragment : Fragment(), FormsAdapter.ITextChanged {

    private lateinit var mRecycler : RecyclerView
    private lateinit var mConfirmBtn : Button

    private lateinit var mViewModel : AdditionalViewModel
    private val app = App.getInstance()
    private lateinit var adapter : FormsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val viewModelFactory = AdditionalViewModelFactory(apiProvider, prefsProvider)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(AdditionalViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_additional, container, false)
        setupUI(rootView)
        setupRecycler()

        mViewModel.getData()

        mViewModel.getFormsObservable().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        mConfirmBtn.setOnClickListener {
            mViewModel.sendData()
            findNavController().navigate(R.id.contacts_info)
        }

        return rootView
    }

    private fun setupRecycler() {
        adapter = FormsAdapter(layoutInflater, this)
        val lm = LinearLayoutManager(requireContext())
        mRecycler.layoutManager = lm
        mRecycler.addItemDecoration(DefaultItemDecoration(resources.getDimension(R.dimen.default_margin).toInt()))
        mRecycler.isNestedScrollingEnabled = false
        mRecycler.adapter = adapter
    }

    override fun textChanged(key: String, value: String) {
        val form = Form(key, value)
        mViewModel.collectData(form)
    }

    private fun setupUI(rootView: View) {
        mRecycler = rootView.findViewById(R.id.forms_recycler)
        mConfirmBtn = rootView.findViewById(R.id.confirm_forms)
    }
}