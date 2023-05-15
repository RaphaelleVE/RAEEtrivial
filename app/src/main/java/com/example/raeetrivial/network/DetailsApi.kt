package com.example.raeetrivial.network

import com.example.raeetrivial.network.model.DetailsApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {

    @GET("/users/{user}")
    suspend fun getDetails(@Path("user") user: String): DetailsApiModel
}