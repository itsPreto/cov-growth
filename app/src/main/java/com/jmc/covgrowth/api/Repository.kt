package com.jmc.covgrowth.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow

class Repository {

    private var client: Webservice = RetrofitClient.retrofit

    @ExperimentalCoroutinesApi
    suspend fun globalSummary() = client.getGlobalSummary()

    @ExperimentalCoroutinesApi
    suspend fun searchCountry() = client.getGlobalSummary()

    @ExperimentalCoroutinesApi
    var dataFlow = flow {
        emit(globalSummary())
    }

    @ExperimentalCoroutinesApi
    var searchFlow = flow {
        emit(globalSummary())
    }


}