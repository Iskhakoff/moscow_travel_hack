package ru.iskhakoff.hackaton.presentation.viewmodel

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Option
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.viewmodel.livedata.SingleLiveEvent

class OptionsServiceViewModel(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModel() {

    private val optionsList = mutableListOf<Option>()

    private val optionsServiceLiveData : SingleLiveEvent<List<Option>> by lazy {
        SingleLiveEvent()
    }

    fun getOptionsServiceObservable() : LiveData<List<Option>> = optionsServiceLiveData

    fun setOptionsService(options : List<Option>) {
        optionsList.clear()
        optionsList.addAll(options)
        optionsServiceLiveData.value = optionsList
    }

    fun setOptionActive(position : Int) {
        optionsList[position] = optionsList[position].setActive(!optionsList[position].active)
        optionsServiceLiveData.value = optionsList
    }

    fun sendServicesIds(mainServiceId : Int, subServicesIds : Set<Int>) {
        CoroutineScope(Dispatchers.IO).launch {
            val listIds = mutableListOf<Int>()
            listIds.add(mainServiceId)
            listIds.addAll(subServicesIds)
            val servicesId = ServicesId(listIds)
            val tokenResponse = apiProvider.sendServicesIds(servicesId)
            if (tokenResponse.isSuccessful) {
                tokenResponse.body()?.let {
                    prefsProvider.setTokenOrder(it.token)
                }
            }
        }
    }
}