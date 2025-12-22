package com.smartglass.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 디바이스 등록 요청
 * api_spec.md: RegisterDeviceData (Request)
 */
@Serializable
data class RegisterDeviceRequest(
    @SerialName("uuid")
    val uuid: String,
    
    @SerialName("deviceId")
    val deviceId: String? = null,
    
    @SerialName("deviceType")
    val deviceType: String, // GLASS, MOBILE
    
    @SerialName("activeStatus")
    val activeStatus: Boolean = true
)
