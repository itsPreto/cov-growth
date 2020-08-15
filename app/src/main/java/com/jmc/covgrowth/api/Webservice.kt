package com.jmc.covgrowth.api

import com.jmc.covgrowth.model.GlobalSummary
import retrofit2.http.GET

interface Webservice {
    @GET("/summary")
   suspend fun getTodo(): GlobalSummary
}