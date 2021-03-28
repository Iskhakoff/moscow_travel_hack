package ru.iskhakoff.hackaton.presentation.models

import java.io.Serializable

data class ServiceEntity(
    val id : Int,
    val name : String,
    val description : String,
    val options : List<OptionServiceEntity>
) : Serializable
