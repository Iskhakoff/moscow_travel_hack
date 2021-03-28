package ru.iskhakoff.hackaton.presentation.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.storage.PrefsProvider
import ru.iskhakoff.hackaton.presentation.viewmodel.ServicesViewModel

@Suppress("UNCHECKED_CAST")
class ServicesViewModelFactory(private val apiProvider: ApiProvider, private val prefsProvider: PrefsProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ServicesViewModel(apiProvider, prefsProvider) as T
    }
}