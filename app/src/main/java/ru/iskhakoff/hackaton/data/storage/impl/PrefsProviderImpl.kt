package ru.iskhakoff.hackaton.data.storage.impl

import android.content.Context
import ru.iskhakoff.hackaton.data.storage.PrefsProvider

class PrefsProviderImpl(context : Context) : PrefsProvider {
    companion object {
        const val PREF_NAME = "moscow_hack"
        const val PREF_KEY_ORDER_TOKEN = "order_token"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun setTokenOrder(tokenOrder: String) {
        preferences.edit().putString(PREF_KEY_ORDER_TOKEN, tokenOrder).apply()
    }

    override fun getTokenOrder(): String {
        preferences.getString(PREF_KEY_ORDER_TOKEN, null)?.let{
            return it
        } ?: run {
            return ""
        }
    }

    override fun removeTokenOrder() {
        preferences.edit().clear().apply()
    }

}