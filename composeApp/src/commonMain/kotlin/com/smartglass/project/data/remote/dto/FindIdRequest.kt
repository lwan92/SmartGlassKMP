package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 아이디 찾기 요청
 * api_spec.md 2.5 참조
 */
@Serializable
data class FindIdRequest(
    val userName: String,
    val phoneNo: String
)

