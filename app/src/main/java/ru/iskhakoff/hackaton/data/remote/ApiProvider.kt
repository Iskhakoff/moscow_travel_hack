package ru.iskhakoff.hackaton.data.remote

import retrofit2.Response
import ru.iskhakoff.hackaton.data.remote.model.request.Contact
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Form
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.remote.model.response.Token
import ru.iskhakoff.hackaton.data.remote.model.response.Total

interface ApiProvider {
    suspend fun getServices() : Response<List<Service>>
    suspend fun sendServicesIds(servicesId: ServicesId) : Response<Token>
    suspend fun sendFlight(token : String, date : String, type : String, number : String) : Response<*>
    suspend fun getForm(token: String) : Response<List<Form>>
    suspend fun sendForm(token : String, forms : List<ru.iskhakoff.hackaton.data.remote.model.request.Form>) : Response<*>
    suspend fun sendContact(token : String, contact: Contact) : Response<*>
    suspend fun getTotal(token : String) : Response<Total>
    suspend fun confirmTotal(token: String, total : ru.iskhakoff.hackaton.data.remote.model.request.Total) : Response<*>
}