package ru.iskhakoff.hackaton.data.storage

interface PrefsProvider {
    fun setTokenOrder(tokenOrder : String)
    fun getTokenOrder() : String
    fun removeTokenOrder()
}