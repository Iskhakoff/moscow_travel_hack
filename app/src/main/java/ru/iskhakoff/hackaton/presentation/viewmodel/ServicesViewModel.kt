package ru.iskhakoff.hackaton.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.models.OptionServiceEntity
import ru.iskhakoff.hackaton.presentation.models.ServiceEntity
import ru.iskhakoff.hackaton.presentation.viewmodel.livedata.SingleLiveEvent
import java.sql.SQLOutput

class ServicesViewModel(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModel() {

    private val servicesLiveData : MutableLiveData<List<Service>> by lazy {
        MutableLiveData()
    }

    private val serviceLiveData : SingleLiveEvent<Service> by lazy {
        SingleLiveEvent()
    }

    fun getServicesObservable() : LiveData<List<Service>> = servicesLiveData
    fun getServiceObservable() : LiveData<Service> = serviceLiveData

    fun getServices(){
        CoroutineScope(Dispatchers.IO).launch {
            val servicesResponse = apiProvider.getServices()
            servicesLiveData.postValue(servicesResponse.body())
        }
    }

    fun getService(id : Int) {
        val list = servicesLiveData.value
        val service = list?.findLast {
            it.id == id
        }
        service?.let {
            serviceLiveData.value = it
        }
    }
}