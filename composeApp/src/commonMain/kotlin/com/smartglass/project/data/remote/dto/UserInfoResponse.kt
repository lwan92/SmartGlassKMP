package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 내 정보 조회 응답
 * api_spec.md 2.10 참조
 */
@Serializable
data class UserInfoResponse(
    val success: Boolean,
    val code: String,
    val data: UserInfoData? = null
)

@Serializable
data class UserInfoData(
    val userNo: Int? = null,
    val loginId: String? = null,
    val userName: String? = null,
    val ognz: OrganizationDto? = null,
    val dept: DepartmentDto? = null,
    val position: PositionDto? = null,
    val role: RoleDto? = null,
    val phoneNo: String? = null,
    val telNo: String? = null,
    val email: String? = null,
    val status: String? = null,
    val lastLoginDt: String? = null,
    val createDt: String? = null,
    val updateDt: String? = null,
    val lockYn: String? = null
)

@Serializable
data class OrganizationDto(
    val ognzId: String? = null,
    val ognzName: String? = null
)

@Serializable
data class DepartmentDto(
    val deptId: String? = null,
    val deptName: String? = null
)

@Serializable
data class PositionDto(
    val positionId: String? = null,
    val positionName: String? = null
)

