package ru.iskhakoff.hackaton.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.iskhakoff.hackaton.App
import ru.iskhakoff.hackaton.R
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.impl.ApiProviderImpl
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.data.storage.impl.PrefsProviderImpl
import ru.iskhakoff.hackaton.presentation.helper.ContactsViewModelFactory
import ru.iskhakoff.hackaton.presentation.viewmodel.ContactsViewModel

class ContactsFragment : Fragment() {

    private lateinit var mViewModel : ContactsViewModel
    private val app = App.getInstance()
    private lateinit var mFullName : EditText
    private lateinit var mEmail : EditText
    private lateinit var mPhone : EditText
    private lateinit var mConfirmBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val viewModelFactory = ContactsViewModelFactory(apiProvider, prefsProvider)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(ContactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)
        setupUI(rootView)

        mViewModel.getCallbackObservable().observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.confirm_info)
            }
        })

        mConfirmBtn.setOnClickListener {
            mViewModel.sendContacts(mFullName.text.toString(), mEmail.text.toString(), mPhone.text.toString())
        }

        return rootView
    }

    private fun setupUI(rootView: View) {
        mFullName = rootView.findViewById(R.id.full_name_field)
        mEmail = rootView.findViewById(R.id.email_field)
        mPhone = rootView.findViewById(R.id.phone_field)
        mConfirmBtn = rootView.findViewById(R.id.confirm_contacts_btn)
    }
}