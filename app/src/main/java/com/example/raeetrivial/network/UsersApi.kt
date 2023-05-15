package com.example.raeetrivial.network

import com.example.raeetrivial.network.model.UserApiModel
import retrofit2.http.GET

interface UsersApi {

    @GET("/repos/square/retrofit/stargazers")
    suspend fun getUsers(): List<UserApiModel>
}