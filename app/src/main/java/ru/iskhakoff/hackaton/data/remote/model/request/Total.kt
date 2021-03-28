package ru.iskhakoff.hackaton.data.remote.model.request

import java.io.Serializable

data class Total(
    var number : String? = null,
    var airport : String? = null,
    var fio : String? = null,
    var email : String? = null,
    var phone : String? = null,
    var service : String? = null,
    var option : String? = null
) : Serializable {

}
