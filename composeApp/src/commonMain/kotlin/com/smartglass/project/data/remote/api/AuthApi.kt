package com.smartglass.project.data.remote.api

import com.smartglass.project.data.network.ApiConstants
import com.smartglass.project.data.remote.dto.LoginRequest
import com.smartglass.project.data.remote.dto.LoginResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApi(private val httpClient: HttpClient) {
    
    suspend fun login(request: LoginRequest): LoginResponse {
        return httpClient.post("${ApiConstants.BASE_URL}${ApiConstants.LOGIN_ENDPOINT}") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
