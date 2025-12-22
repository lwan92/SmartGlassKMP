package com.smartglass.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 앱 등록 요청
 * api_spec.md: RegisterAppData (Request)
 */
@Serializable
data class RegisterAppRequest(
    @SerialName("deviceId")
    val deviceId: String,
    
    @SerialName("appId")
    val appId: String,
    
    @SerialName("appType")
    val appType: String, // GLASS, MOBILE
    
    @SerialName("appVersion")
    val appVersion: String
)
