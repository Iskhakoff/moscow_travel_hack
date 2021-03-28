package ru.iskhakoff.hackaton

import android.app.Application
import ru.iskhakoff.hackaton.presentation.models.FlightInfo

class App : Application(){

    private var mainServiceId = -1
    private val listSubServiceIds = mutableSetOf<Int>()
    private var flightInfo: FlightInfo? = null

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
        listSubServiceIds.clear()
        listSubServiceIds.add(id)
    }

    fun removeSubServicesIds() {
        listSubServiceIds.clear()
    }

    fun getFlightInfo() : FlightInfo? {
        return flightInfo
    }

    fun setFlightInfo(direction : String, date : String, numberFlight : String?) {
        flightInfo = FlightInfo(direction, date, numberFlight)
    }

    fun removeFlightInfo() {
        flightInfo = null
    }
}