package ru.iskhakoff.hackaton.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.model.response.Form
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.viewmodel.livedata.SingleLiveEvent

class AdditionalViewModel(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModel() {

    private val values = mutableListOf<ru.iskhakoff.hackaton.data.remote.model.request.Form>()

    private val formsLiveData : SingleLiveEvent<List<Form>> by lazy {
        SingleLiveEvent()
    }

    fun getFormsObservable() : LiveData<List<Form>> {
        return formsLiveData
    }

    fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseForms = apiProvider.getForm(prefsProvider.getTokenOrder())
            formsLiveData.postValue(responseForms.body())
        }
    }

    fun collectData(form : ru.iskhakoff.hackaton.data.remote.model.request.Form) {
        values.add(form)
    }

    fun sendData() {
        CoroutineScope(Dispatchers.IO).launch {
            val requestForm = apiProvider.sendForm(prefsProvider.getTokenOrder(), values)
        }
    }

}