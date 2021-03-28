package ru.iskhakoff.hackaton.data.remote.service

import retrofit2.Response
import retrofit2.http.*
import ru.iskhakoff.hackaton.data.remote.model.request.Contact
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Form
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.remote.model.response.Token
import ru.iskhakoff.hackaton.data.remote.model.response.Total

private const val GET_SERVICES = "services"
private const val SEND_SERVICES_IDS = "services"
private const val SEND_FLIGHT = "flight"
private const val GET_FORM = "form"
private const val SEND_FORM = "form"
private const val SEND_CONTACT = "contact"
private const val GET_ORDER = "order"
private const val CONFIRM_ORDER = "order"
interface ApiService {
    @GET(GET_SERVICES)
    suspend fun getServices() : Response<List<Service>>
    @POST(SEND_SERVICES_IDS)
    suspend fun sendServicesIds(@Body servicesId: ServicesId) : Response<Token>
    @GET(SEND_FLIGHT)
    suspend fun sendFlight(@Header("Authorization") token : String, @Query("date") date : String, @Query("type") direction : String, @Query("number") flightNumber : String) : Response<*>
    @GET(GET_FORM)
    suspend fun getForm(@Header("Authorization") token : String) : Response<List<Form>>
    @POST(SEND_FORM)
    suspend fun sendForm(@Header("Authorization") token : String, @Body forms : List<ru.iskhakoff.hackaton.data.remote.model.request.Form>) : Response<*>
    @POST(SEND_CONTACT)
    suspend fun sendContact(@Header("Authorization") token: String, @Body contact : Contact) : Response<*>
    @GET(GET_ORDER)
    suspend fun getOrder(@Header("Authorization") token : String) : Response<Total>
    @POST(CONFIRM_ORDER)
    suspend fun confirmOrder(@Header("Authorization") token : String, @Body total : ru.iskhakoff.hackaton.data.remote.model.request.Total) : Response<*>
}