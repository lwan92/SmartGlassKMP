package com.smartglass.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 디바이스 등록 요청
 * api_spec.md: RegisterDeviceData (Request)
 * 
 * deviceId는 선택적 필드이며, 초기 등록 시 null일 수 있습니다.
 * explicitNulls = false 설정으로 null 값은 JSON에 포함되지 않습니다.
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
