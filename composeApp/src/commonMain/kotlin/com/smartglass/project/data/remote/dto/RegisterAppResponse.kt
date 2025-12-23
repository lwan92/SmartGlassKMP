package com.smartglass.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 앱 등록 응답
 * api_spec.md: RegisterAppResponse (Response)
 */
@Serializable
data class RegisterAppResponse(
    @SerialName("success")
    val success: Boolean,
    
    @SerialName("code")
    val code: String,
    
    @SerialName("data")
    val data: AppData? = null
)

@Serializable
data class AppData(
    @SerialName("appInfo")
    val appInfo: AppInfo? = null
)

@Serializable
data class AppInfo(
    @SerialName("deviceId")
    val deviceId: String? = null,
    
    @SerialName("appId")
    val appId: String? = null,
    
    @SerialName("appType")
    val appType: String? = null,
    
    @SerialName("appVersion")
    val appVersion: String? = null
)
