package ru.iskhakoff.hackaton.data.remote.impl

import retrofit2.Response
import ru.iskhakoff.hackaton.data.remote.ApiProvider
import ru.iskhakoff.hackaton.data.remote.helper.RetrofitFactory
import ru.iskhakoff.hackaton.data.remote.model.request.Contact
import ru.iskhakoff.hackaton.data.remote.model.request.ServicesId
import ru.iskhakoff.hackaton.data.remote.model.response.Form
import ru.iskhakoff.hackaton.data.remote.model.response.Service
import ru.iskhakoff.hackaton.data.remote.model.response.Token
import ru.iskhakoff.hackaton.data.remote.model.response.Total

class ApiProviderImpl : ApiProvider {
    override suspend fun getServices(): Response<List<Service>> {
        return RetrofitFactory.getService().getServices()
    }

    override suspend fun sendServicesIds(servicesId: ServicesId): Response<Token> {
        return RetrofitFactory.getService().sendServicesIds(servicesId)
    }

    override suspend fun sendFlight(token : String, date: String, type: String, number: String): Response<*> {
        return RetrofitFactory.getService().sendFlight(token, date, type, number)
    }

    override suspend fun getForm(token: String): Response<List<Form>> {
        return RetrofitFactory.getService().getForm(token)
    }

    override suspend fun sendForm(
        token: String,
        forms: List<ru.iskhakoff.hackaton.data.remote.model.request.Form>
    ): Response<*> {
        return RetrofitFactory.getService().sendForm(token, forms)
    }

    override suspend fun sendContact(token: String, contact: Contact): Response<*> {
        return RetrofitFactory.getService().sendContact(token, contact)
    }

    override suspend fun getTotal(token: String): Response<Total> {
        return RetrofitFactory.getService().getOrder(token)
    }

    override suspend fun confirmTotal(
        token: String,
        total: ru.iskhakoff.hackaton.data.remote.model.request.Total
    ): Response<*> {
        return RetrofitFactory.getService().confirmOrder(token, total)
    }
}