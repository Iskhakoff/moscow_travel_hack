package ru.iskhakoff.hackaton.data.remote.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.remote.model.response.Token

private const val GET_SERVICES = "services"
private const val SEND_SERVICES_IDS = "services"
interface ApiService {
    @GET(GET_SERVICES)
    suspend fun getServices() : Response<List<Service>>
    @POST(SEND_SERVICES_IDS)
    suspend fun sendServicesIds(@Body servicesId: ServicesId) : Response<Token>
}