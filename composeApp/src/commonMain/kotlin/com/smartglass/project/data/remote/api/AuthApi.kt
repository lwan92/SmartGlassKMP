package com.smartglass.project.data.remote.api

import com.smartglass.project.data.network.ApiConstants
import com.smartglass.project.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class AuthApi(private val httpClient: HttpClient) {
    
    private val json = Json { 
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    
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
        println("🌐 AuthApi: registerDevice 호출")
        println("🌐 URL: ${ApiConstants.BASE_URL}/api/device/regist/device")
        println("🌐 Request Body (JSON):")
        try {
            val requestJson = json.encodeToString(RegisterDeviceRequest.serializer(), request)
            println(requestJson)
        } catch (e: Exception) {
            println("  JSON 직렬화 실패: ${e.message}")
        }
        
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
        println("🌐 AuthApi: registerApp 호출")
        println("🌐 URL: ${ApiConstants.BASE_URL}/api/device/regist/app")
        println("🌐 Request Body (JSON):")
        try {
            val requestJson = json.encodeToString(RegisterAppRequest.serializer(), request)
            println(requestJson)
        } catch (e: Exception) {
            println("  JSON 직렬화 실패: ${e.message}")
        }
        
        return httpClient.post("${ApiConstants.BASE_URL}/api/device/regist/app") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
