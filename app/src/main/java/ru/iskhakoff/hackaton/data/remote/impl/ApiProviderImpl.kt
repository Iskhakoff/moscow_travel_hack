package ru.iskhakoff.hackaton.data.remote.impl

import retrofit2.Response
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.helper.RetrofitFactory
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.remote.model.response.Token

class ApiProviderImpl : ApiProvider {
    override suspend fun getServices(): Response<List<Service>> {
        return RetrofitFactory.getService().getServices()
    }

    override suspend fun sendServicesIds(servicesId: ServicesId): Response<Token> {
        return RetrofitFactory.getService().sendServicesIds(servicesId)
    }
}