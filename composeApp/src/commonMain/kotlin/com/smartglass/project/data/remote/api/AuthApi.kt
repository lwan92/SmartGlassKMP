package com.smartglass.project.data.remote.api

import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.data.network.ApiConstants
import com.smartglass.project.data.network.BaseUrlConfig
import com.smartglass.project.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class AuthApi(
    private val httpClient: HttpClient,
    private val preferencesManager: PreferencesManager
) {
    private val baseUrlConfig = BaseUrlConfig(preferencesManager)
    
    private val json = Json { 
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true  // ✅ null 값도 JSON에 포함
    }
    
    private suspend fun getBaseUrl(): String {
        return baseUrlConfig.getBaseUrl()
    }
    
    suspend fun login(request: LoginRequest): LoginResponse {
        return httpClient.post("${getBaseUrl()}${ApiConstants.LOGIN_ENDPOINT}") {
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
        println("🌐 URL: ${getBaseUrl()}/api/device/regist/device")
        println("🌐 Request Body (JSON):")
        try {
            val requestJson = json.encodeToString(RegisterDeviceRequest.serializer(), request)
            println(requestJson)
        } catch (e: Exception) {
            println("  JSON 직렬화 실패: ${e.message}")
        }
        
        return httpClient.post("${getBaseUrl()}/api/device/regist/device") {
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
        println("🌐 URL: ${getBaseUrl()}/api/device/regist/app")
        println("🌐 Request Body (JSON):")
        try {
            val requestJson = json.encodeToString(RegisterAppRequest.serializer(), request)
            println(requestJson)
        } catch (e: Exception) {
            println("  JSON 직렬화 실패: ${e.message}")
        }
        
        return httpClient.post("${getBaseUrl()}/api/device/regist/app") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    /**
     * QR 로그인
     * api_spec.md 2.2: POST /api/auth/qr-login
     */
    suspend fun qrLogin(request: QrLoginRequest): LoginResponse {
        return httpClient.post("${getBaseUrl()}/api/auth/qr-login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    /**
     * 로그아웃
     * api_spec.md 2.3: POST /api/auth/logout
     */
    suspend fun logout(): LogoutResponse {
        val accessToken = preferencesManager.getAccessToken()
        return httpClient.post("${getBaseUrl()}/api/auth/logout") {
            contentType(ContentType.Application.Json)
            if (accessToken != null) {
                header(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }.body()
    }
    
    /**
     * 토큰 갱신
     * api_spec.md 2.4: POST /api/auth/token/refresh
     */
    suspend fun refreshToken(request: TokenRefreshRequest): LoginResponse {
        return httpClient.post("${getBaseUrl()}/api/auth/token/refresh") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    /**
     * 아이디 찾기
     * api_spec.md 2.5: POST /api/auth/find-id
     */
    suspend fun findId(request: FindIdRequest): FindIdResponse {
        return httpClient.post("${getBaseUrl()}/api/auth/find-id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    /**
     * 비밀번호 찾기
     * api_spec.md 2.6: POST /api/auth/find-password
     */
    suspend fun findPassword(request: FindPasswordRequest): FindPasswordResponse {
        return httpClient.post("${getBaseUrl()}/api/auth/find-password") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    
    /**
     * 비밀번호 변경
     * api_spec.md 2.7: PUT /api/users/password
     */
    suspend fun changePassword(request: ChangePasswordRequest): ChangePasswordResponse {
        val accessToken = preferencesManager.getAccessToken()
        return httpClient.put("${getBaseUrl()}/api/users/password") {
            contentType(ContentType.Application.Json)
            if (accessToken != null) {
                header(HttpHeaders.Authorization, "Bearer $accessToken")
            }
            setBody(request)
        }.body()
    }
    
    /**
     * 내 정보 조회
     * api_spec.md 2.10: GET /api/users/me
     */
    suspend fun getUserInfo(): UserInfoResponse {
        val accessToken = preferencesManager.getAccessToken()
        return httpClient.get("${getBaseUrl()}/api/users/me") {
            contentType(ContentType.Application.Json)
            if (accessToken != null) {
                header(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }.body()
    }
}
