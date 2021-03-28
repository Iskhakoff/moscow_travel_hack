package ru.iskhakoff.hackaton.data.remote.model.response

import java.io.Serializable

data class Service(
    val id : Int,
    val name : String,
    val children : List<Option>,
    val description : String = "Описание"
) : Serializable