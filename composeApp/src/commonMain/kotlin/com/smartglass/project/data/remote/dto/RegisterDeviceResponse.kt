package com.smartglass.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 디바이스 등록 응답
 * api_spec.md: RegisterDeviceResponse (Response)
 */
@Serializable
data class RegisterDeviceResponse(
    @SerialName("success")
    val success: Boolean,
    
    @SerialName("code")
    val code: String,
    
    @SerialName("data")
    val data: DeviceData? = null
)

@Serializable
data class DeviceData(
    @SerialName("device")
    val device: Device? = null
)

@Serializable
data class Device(
    @SerialName("deviceId")
    val deviceId: String? = null,
    
    @SerialName("deviceName")
    val deviceName: String? = null,
    
    @SerialName("modelName")
    val modelName: String? = null,
    
    @SerialName("serialNumber")
    val serialNumber: String? = null,
    
    @SerialName("osVersion")
    val osVersion: String? = null,
    
    @SerialName("osType")
    val osType: String? = null,
    
    @SerialName("isActive")
    val isActive: Boolean? = null
)
