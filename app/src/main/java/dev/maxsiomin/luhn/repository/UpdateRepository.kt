package dev.maxsiomin.luhn.repository

import dev.maxsiomin.luhn.extensions.notNull
import dev.maxsiomin.luhn.network.UpdateApi
import dev.maxsiomin.luhn.util.UiActions
import dev.maxsiomin.luhn.util.addOnCompleteListener
import timber.log.Timber

class UpdateRepository(uiActions: UiActions, private val callback: (UpdateResponse) -> Unit) : BaseRepository(uiActions) {

    /**
     * Searches for last version of app on my server
     */
    fun getLastVersion() {
        UpdateApi.retrofitService.getLastVersion().addOnCompleteListener { result ->

            // In case of server side error, body might be null
            if (result.isSuccessful && result.response?.body() != null) {
                callback(Success(result.response.body().toString()))
            } else {
                Timber.e(result.t)
                val errorMessage = result.t?.message?.notNull()
                callback(Failure(isConnectionError(errorMessage), errorMessage))
            }
        }
    }
}

sealed class UpdateResponse

data class Success(val latestVersionName: String) : UpdateResponse()

data class Failure (

    val connectionError: Boolean,

    val errorMessage: String?,

) : UpdateResponse()
