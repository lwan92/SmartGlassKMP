package com.smartglass.project.data.remote.api

import com.smartglass.project.data.network.ApiConstants
import com.smartglass.project.data.remote.dto.*
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
    
    /**
     * 디바이스 등록
     * api_spec.md: POST /api/device/regist/device
     */
    suspend fun registerDevice(request: RegisterDeviceRequest): RegisterDeviceResponse {
        return httpClient.post("${ApiConstants.BASE_URL}/api/device/regist/device") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    /**
     * 앱 등록
     * api_spec.md: POST /api/device/regist/app
     */
    suspend fun registerApp(request: RegisterAppRequest): RegisterAppResponse {
        return httpClient.post("${ApiConstants.BASE_URL}/api/device/regist/app") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
