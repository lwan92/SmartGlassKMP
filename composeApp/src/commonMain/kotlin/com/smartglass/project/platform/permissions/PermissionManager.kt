package com.smartglass.project.platform.permissions

/**
 * 플랫폼별 권한 관리자
 * iOS는 카메라/마이크 권한 관리
 * Android는 추가로 오버레이 등 다양한 권한
 */
interface PermissionManager {
    suspend fun checkPermissions(): Boolean
    suspend fun requestPermissions(): Boolean
    suspend fun checkCameraPermission(): Boolean
    suspend fun requestCameraPermission(): Boolean
}

expect fun createPermissionManager(): PermissionManager
