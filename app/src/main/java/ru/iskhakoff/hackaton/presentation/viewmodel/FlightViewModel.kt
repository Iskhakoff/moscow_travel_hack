package ru.iskhakoff.hackaton.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.viewmodel.livedata.SingleLiveEvent

class FlightViewModel(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModel() {

    private val stateLiveData : SingleLiveEvent<Boolean> by lazy {
        SingleLiveEvent()
    }

    fun getStateObservable() : LiveData<Boolean> {
        return stateLiveData
    }

    fun sendFlight(direction : String, date : String, numberFlight : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiProvider.sendFlight(prefsProvider.getTokenOrder(), date, direction, numberFlight)
            if (response.code() in 400..499) {
                stateLiveData.postValue(false)
            }else {
                stateLiveData.postValue(true)
            }
        }
    }

}