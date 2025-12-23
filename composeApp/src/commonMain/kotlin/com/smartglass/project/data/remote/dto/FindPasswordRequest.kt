package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 비밀번호 찾기 요청
 * api_spec.md 2.6 참조
 */
@Serializable
data class FindPasswordRequest(
    val loginId: String,
    val userName: String,
    val phoneNo: String
)

