package ru.iskhakoff.hackaton.data.remote.model.response

data class Total(
    val number : String,
    val airport: String,
    val fio : String,
    val email : String,
    val phone : String,
    val service : ServiceTotal,
    val option : String
)
