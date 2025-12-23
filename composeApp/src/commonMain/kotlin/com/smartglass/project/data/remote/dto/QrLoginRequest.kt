package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * QR 로그인 요청
 * api_spec.md 2.2 참조
 */
@Serializable
data class QrLoginRequest(
    val loginId: String,
    val uuid: String,
    val deviceType: String, // "MOBILE" 또는 "MOBILE_NEO"
    val allowDuplicateLogin: Boolean,
    val appId: String
)

