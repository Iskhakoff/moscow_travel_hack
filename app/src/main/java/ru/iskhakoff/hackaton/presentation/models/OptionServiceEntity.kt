package ru.iskhakoff.hackaton.presentation.models

import java.io.Serializable

data class OptionServiceEntity(
    val id : Int,
    val name : String,
    val description : String,
    val active : Boolean = false
) : Serializable
