package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 비밀번호 변경 요청
 * api_spec.md 2.7 참조
 */
@Serializable
data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)

