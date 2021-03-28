package ru.iskhakoff.hackaton.data.remote

import retrofit2.Response
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.remote.model.response.Token

interface ApiProvider {
    suspend fun getServices() : Response<List<Service>>
    suspend fun sendServicesIds(servicesId: ServicesId) : Response<Token>
}