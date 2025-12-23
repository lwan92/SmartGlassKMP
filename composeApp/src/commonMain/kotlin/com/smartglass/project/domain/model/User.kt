package com.smartglass.project.domain.model

data class User(
    val userNo: Int? = null,
    val userId: String,
    val loginId: String,
    val deptName: String,
    val ognzName: String,
    val positionName: String,
    val userName: String,
    val role: Role
)
