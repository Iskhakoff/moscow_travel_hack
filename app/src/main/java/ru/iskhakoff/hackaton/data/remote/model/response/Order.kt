package ru.iskhakoff.hackaton.data.remote.model.response

data class Order(
    val number : String,
    val airport : Airport,
    val fio : String,
    val phone : String,
    val service : String,
    val option : String
)
