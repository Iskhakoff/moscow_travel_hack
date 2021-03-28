package ru.iskhakoff.hackaton.data.remote.model.response

import java.io.Serializable

data class Option(
    val id : Int,
    val name : String,
    val description : String,
    var active : Boolean = false
) : Serializable {
    fun setActive(isActive : Boolean) : Option {
        return Option(id = this.id, name = this.name, active = isActive, description = this.description)
    }
}
