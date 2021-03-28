package ru.iskhakoff.hackaton.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.model.request.Contact
import ru.iskhakoff.hackaton.data.remote.model.request.ContactInfo
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.viewmodel.livedata.SingleLiveEvent

class ContactsViewModel(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModel() {

    private val callBackLiveData : SingleLiveEvent<Boolean> by lazy {
        SingleLiveEvent()
    }

    fun getCallbackObservable() : LiveData<Boolean> {
        return callBackLiveData
    }

    fun sendContacts(fullName : String, email : String, phone : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val contactInfo = ContactInfo(fullName, email, phone)
            val contact = Contact(contactInfo)

            apiProvider.sendContact(prefsProvider.getTokenOrder(), contact)
            callBackLiveData.postValue(true)
        }
    }

}