package ru.iskhakoff.hackaton.presentation.viewmodel

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.iskhakoff.hackaton.App
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

    private val stateProvidedLiveData : SingleLiveEvent<Boolean> by lazy {
        SingleLiveEvent()
    }

    fun getOptionsServiceObservable() : LiveData<List<Option>> = optionsServiceLiveData
    fun getStateProvidedObservable() : LiveData<Boolean> = stateProvidedLiveData

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
        CoroutineScope(Dispatchers.IO).launch(handler) {
            val listIds = mutableListOf<Int>()
            listIds.add(mainServiceId)
            listIds.addAll(subServicesIds)
            val servicesId = ServicesId(listIds)
            val tokenResponse = apiProvider.sendServicesIds(servicesId)

            if(tokenResponse.code() == 404) {
                stateProvidedLiveData.postValue(false)
            } else {
                tokenResponse.body()?.let {
                    prefsProvider.removeTokenOrder()
                    prefsProvider.setTokenOrder(it.token)
                    stateProvidedLiveData.postValue(true)
                } ?: run {
                    stateProvidedLiveData.postValue(false)
                }
            }


        }
    }

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        stateProvidedLiveData.postValue(false)
    }
}