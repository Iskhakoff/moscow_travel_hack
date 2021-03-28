package ru.iskhakoff.hackaton

import android.app.Application

class App : Application(){

    private var mainServiceId = -1
    private val listSubServiceIds = mutableSetOf<Int>()

    companion object {
        private lateinit var instance : App
        fun getInstance() : App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getMainServiceId() : Int {
        return mainServiceId
    }

    fun setMainServiceId(id : Int) {
        mainServiceId = id
    }

    fun removeMainServiceId() {
        mainServiceId = -1
    }

    fun getSubServicesIds() : Set<Int> {
        return listSubServiceIds
    }

    fun setSubServiceId(id : Int) {
        listSubServiceIds.add(id)
    }

    fun removeSubServicesIds() {
        listSubServiceIds.clear()
    }
}