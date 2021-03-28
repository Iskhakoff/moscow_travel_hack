package ru.iskhakoff.hackaton.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.model.request.Total
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.viewmodel.livedata.SingleLiveEvent

class ConfirmViewModel(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModel() {

    private val totalLiveData : SingleLiveEvent<List<Pair<String, String>>> by lazy {
        SingleLiveEvent()
    }

    private val statusConfirmed : SingleLiveEvent<Boolean> by lazy {
        SingleLiveEvent()
    }

    fun getTotalObservable() : LiveData<List<Pair<String, String>>> {
        return totalLiveData
    }

    fun getStatusConfirmedObservable() : LiveData<Boolean> {
        return statusConfirmed
    }

    fun getOrder() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseTotal = apiProvider.getTotal(prefsProvider.getTokenOrder())
            val listFields = mutableListOf<Pair<String, String>>()
            responseTotal.body()?.let {
                listFields.add(Pair("ФИО", it.fio))
                listFields.add(Pair("Рейс", it.number))
                listFields.add(Pair("Аэропорт", it.airport))
                listFields.add(Pair("Услуга", it.service.name))
                listFields.add(Pair("Опции", it.option))
                listFields.add(Pair("Номер телефона", it.phone))
                listFields.add(Pair("E-mail", it.email))
            }

            totalLiveData.postValue(listFields)
        }
    }

    fun confirmOrder() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = mutableListOf<Pair<String, String>>()
            totalLiveData.value?.let {
                list.addAll(it)
            }

            val total = Total()

            list.forEach {
                when(it.first) {
                    "ФИО" -> {
                        total.fio = it.second
                    }
                    "Рейс" -> {
                        total.number = it.second
                    }
                    "Аэропорт" -> {
                        total.airport = it.second
                    }
                    "Услуга" -> {
                        total.service = it.second
                    }
                    "Опции" -> {
                        total.option = it.second
                    }
                    "Номер телефона" -> {
                        total.phone = it.second
                    }
                    "E-mail" -> {
                        total.email = it.second
                    }
                }
            }

            val totalResponse = apiProvider.confirmTotal(prefsProvider.getTokenOrder(), total)

            if (totalResponse.isSuccessful) {
                statusConfirmed.postValue(true)
            }
        }
    }

}